package com.downly_app;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.RemoteViews;

import androidx.core.app.NotificationCompat;
import androidx.core.content.FileProvider;

import com.coremedia.iso.BoxParser;
import com.coremedia.iso.IsoFile;
import com.coremedia.iso.IsoTypeWriter;
import com.coremedia.iso.boxes.Box;
import com.coremedia.iso.boxes.ChunkOffsetBox;
import com.coremedia.iso.boxes.CompositionTimeToSample;
import com.coremedia.iso.boxes.Container;
import com.coremedia.iso.boxes.DataEntryUrlBox;
import com.coremedia.iso.boxes.DataInformationBox;
import com.coremedia.iso.boxes.DataReferenceBox;
import com.coremedia.iso.boxes.EditBox;
import com.coremedia.iso.boxes.EditListBox;
import com.coremedia.iso.boxes.FileTypeBox;
import com.coremedia.iso.boxes.HandlerBox;
import com.coremedia.iso.boxes.HintMediaHeaderBox;
import com.coremedia.iso.boxes.MediaBox;
import com.coremedia.iso.boxes.MediaHeaderBox;
import com.coremedia.iso.boxes.MediaInformationBox;
import com.coremedia.iso.boxes.MovieBox;
import com.coremedia.iso.boxes.MovieHeaderBox;
import com.coremedia.iso.boxes.NullMediaHeaderBox;
import com.coremedia.iso.boxes.SampleDependencyTypeBox;
import com.coremedia.iso.boxes.SampleSizeBox;
import com.coremedia.iso.boxes.SampleTableBox;
import com.coremedia.iso.boxes.SampleToChunkBox;
import com.coremedia.iso.boxes.SoundMediaHeaderBox;
import com.coremedia.iso.boxes.StaticChunkOffsetBox;
import com.coremedia.iso.boxes.SubtitleMediaHeaderBox;
import com.coremedia.iso.boxes.SyncSampleBox;
import com.coremedia.iso.boxes.TimeToSampleBox;
import com.coremedia.iso.boxes.TrackBox;
import com.coremedia.iso.boxes.TrackHeaderBox;
import com.coremedia.iso.boxes.VideoMediaHeaderBox;
import com.googlecode.mp4parser.BasicContainer;
import com.googlecode.mp4parser.DataSource;
import com.googlecode.mp4parser.authoring.Edit;
import com.googlecode.mp4parser.authoring.Movie;
import com.googlecode.mp4parser.authoring.Sample;
import com.googlecode.mp4parser.authoring.Track;
import com.googlecode.mp4parser.authoring.builder.BetterFragmenter;
import com.googlecode.mp4parser.authoring.builder.DefaultMp4Builder;
import com.googlecode.mp4parser.authoring.builder.Fragmenter;
import com.googlecode.mp4parser.authoring.builder.Mp4Builder;
import com.googlecode.mp4parser.authoring.tracks.CencEncryptedTrack;
import com.googlecode.mp4parser.boxes.dece.SampleEncryptionBox;
import com.googlecode.mp4parser.boxes.mp4.samplegrouping.GroupEntry;
import com.googlecode.mp4parser.boxes.mp4.samplegrouping.SampleGroupDescriptionBox;
import com.googlecode.mp4parser.boxes.mp4.samplegrouping.SampleToGroupBox;
import com.googlecode.mp4parser.util.Logger;
import com.googlecode.mp4parser.util.Mp4Arrays;
import com.googlecode.mp4parser.util.Path;
import com.mp4parser.iso14496.part12.SampleAuxiliaryInformationOffsetsBox;
import com.mp4parser.iso14496.part12.SampleAuxiliaryInformationSizesBox;
import com.mp4parser.iso23001.part7.CencSampleAuxiliaryDataFormat;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.WritableByteChannel;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.googlecode.mp4parser.util.CastUtils.l2i;
import static com.googlecode.mp4parser.util.Math.lcm;

public class DefaultMp4Builder1 implements Mp4Builder {

    public Context context;
    public String path="",ctime="";
    public DataBase db;
    public DefaultMp4Builder1(Context cntx,String path,String ctime) {
        context=cntx;
        db=new DataBase(context);
        this.path=path;
        this.ctime=ctime;
    }

    private static Logger LOG = Logger.getLogger(DefaultMp4Builder.class);
    Map<Track, StaticChunkOffsetBox> chunkOffsetBoxes = new HashMap<Track, StaticChunkOffsetBox>();
    Set<SampleAuxiliaryInformationOffsetsBox> sampleAuxiliaryInformationOffsetsBoxes = new HashSet<SampleAuxiliaryInformationOffsetsBox>();
    HashMap<Track, List<Sample>> track2Sample = new HashMap<Track, List<Sample>>();
    HashMap<Track, long[]> track2SampleSizes = new HashMap<Track, long[]>();
    private Fragmenter fragmenter;

    private static long sum(int[] ls) {
        long rc = 0;
        for (long l : ls) {
            rc += l;
        }
        return rc;
    }

    private static long sum(long[] ls) {
        long rc = 0;
        for (long l : ls) {
            rc += l;
        }
        return rc;
    }

    public static long gcd(long a, long b) {
        if (b == 0) {
            return a;
        }
        return gcd(b, a % b);
    }

    public void setFragmenter(Fragmenter fragmenter) {
        this.fragmenter = fragmenter;
    }

    /**
     * {@inheritDoc}
     */
    public Container build(Movie movie) {
        if (fragmenter == null) {
            fragmenter = new BetterFragmenter(2);
        }
        LOG.logDebug("Creating movie " + movie);
        for (Track track : movie.getTracks()) {
            // getting the samples may be a time consuming activity
            List<Sample> samples = track.getSamples();
            putSamples(track, samples);
            long[] sizes = new long[samples.size()];
            for (int i = 0; i < sizes.length; i++) {
                Sample b = samples.get(i);
                sizes[i] = b.getSize();
            }
            track2SampleSizes.put(track, sizes);

        }

        BasicContainer isoFile = new BasicContainer();

        isoFile.addBox(createFileTypeBox(movie));

        Map<Track, int[]> chunks = new HashMap<Track, int[]>();
        for (Track track : movie.getTracks()) {
            chunks.put(track, getChunkSizes(track));
        }
        Box moov = createMovieBox(movie, chunks);
        isoFile.addBox(moov);
        List<SampleSizeBox> stszs = Path.getPaths(moov, "trak/mdia/minf/stbl/stsz");

        long contentSize = 0;
        for (SampleSizeBox stsz : stszs) {
            contentSize += sum(stsz.getSampleSizes());

        }
        LOG.logDebug("About to create mdat");
        DefaultMp4Builder1.InterleaveChunkMdat mdat = new DefaultMp4Builder1.InterleaveChunkMdat(movie, chunks, contentSize);
        isoFile.addBox(mdat);
        LOG.logDebug("mdat crated");

        /*
        dataOffset is where the first sample starts. In this special mdat the samples always start
        at offset 16 so that we can use the same offset for large boxes and small boxes
         */
        long dataOffset = mdat.getDataOffset();
        for (StaticChunkOffsetBox chunkOffsetBox : chunkOffsetBoxes.values()) {
            long[] offsets = chunkOffsetBox.getChunkOffsets();
            for (int i = 0; i < offsets.length; i++) {
                offsets[i] += dataOffset;
            }
        }
        for (SampleAuxiliaryInformationOffsetsBox saio : sampleAuxiliaryInformationOffsetsBoxes) {
            long offset = saio.getSize(); // the calculation is systematically wrong by 4, I don't want to debug why. Just a quick correction --san 14.May.13
            offset += 4 + 4 + 4 + 4 + 4 + 24;
            // size of all header we were missing otherwise (moov, trak, mdia, minf, stbl)
            Object b = saio;
            do {
                Object current = b;
                b = ((Box) b).getParent();

                for (Box box : ((Container) b).getBoxes()) {
                    if (box == current) {
                        break;
                    }
                    offset += box.getSize();
                }

            } while (b instanceof Box);

            long[] saioOffsets = saio.getOffsets();
            for (int i = 0; i < saioOffsets.length; i++) {
                saioOffsets[i] = saioOffsets[i] + offset;

            }
            saio.setOffsets(saioOffsets);
        }


        return isoFile;
    }

    protected List<Sample> putSamples(Track track, List<Sample> samples) {
        return track2Sample.put(track, samples);
    }

    protected FileTypeBox createFileTypeBox(Movie movie) {
        List<String> minorBrands = new LinkedList<String>();

        minorBrands.add("mp42");
        minorBrands.add("iso6");
        minorBrands.add("avc1");
        minorBrands.add("isom");
        return new FileTypeBox("iso6", 1, minorBrands);
    }

    protected MovieBox createMovieBox(Movie movie, Map<Track, int[]> chunks) {
        MovieBox movieBox = new MovieBox();
        MovieHeaderBox mvhd = new MovieHeaderBox();

        mvhd.setCreationTime(new Date());
        mvhd.setModificationTime(new Date());
        mvhd.setMatrix(movie.getMatrix());
        long movieTimeScale = getTimescale(movie);
        long duration = 0;

        for (Track track : movie.getTracks()) {
            long tracksDuration;

            if (track.getEdits() == null || track.getEdits().isEmpty()) {
                tracksDuration = (track.getDuration() * movieTimeScale / track.getTrackMetaData().getTimescale());
            } else {
                double d = 0;
                for (Edit edit : track.getEdits()) {
                    d += (long) edit.getSegmentDuration();
                }
                tracksDuration = (long) (d * movieTimeScale);
            }


            if (tracksDuration > duration) {
                duration = tracksDuration;
            }


        }

        mvhd.setDuration(duration);
        mvhd.setTimescale(movieTimeScale);
        // find the next available trackId
        long nextTrackId = 0;
        for (Track track : movie.getTracks()) {
            nextTrackId = nextTrackId < track.getTrackMetaData().getTrackId() ? track.getTrackMetaData().getTrackId() : nextTrackId;
        }
        mvhd.setNextTrackId(++nextTrackId);

        movieBox.addBox(mvhd);
        for (Track track : movie.getTracks()) {
            movieBox.addBox(createTrackBox(track, movie, chunks));
        }
        // metadata here
        Box udta = createUdta(movie);
        if (udta != null) {
            movieBox.addBox(udta);
        }
        return movieBox;

    }

    /**
     * Override to create a user data box that may contain metadata.
     *
     * @param movie source movie
     * @return a 'udta' box or <code>null</code> if none provided
     */
    protected Box createUdta(Movie movie) {
        return null;
    }

    protected TrackBox createTrackBox(Track track, Movie movie, Map<Track, int[]> chunks) {

        TrackBox trackBox = new TrackBox();
        TrackHeaderBox tkhd = new TrackHeaderBox();

        tkhd.setEnabled(true);
        tkhd.setInMovie(true);
//        tkhd.setInPreview(true);
//        tkhd.setInPoster(true);
        tkhd.setMatrix(track.getTrackMetaData().getMatrix());

        tkhd.setAlternateGroup(track.getTrackMetaData().getGroup());
        tkhd.setCreationTime(track.getTrackMetaData().getCreationTime());

        if (track.getEdits() == null || track.getEdits().isEmpty()) {
            tkhd.setDuration(track.getDuration() * getTimescale(movie) / track.getTrackMetaData().getTimescale());
        } else {
            long d = 0;
            for (Edit edit : track.getEdits()) {
                d += (long) edit.getSegmentDuration();
            }
            tkhd.setDuration(d * track.getTrackMetaData().getTimescale());
        }


        tkhd.setHeight(track.getTrackMetaData().getHeight());
        tkhd.setWidth(track.getTrackMetaData().getWidth());
        tkhd.setLayer(track.getTrackMetaData().getLayer());
        tkhd.setModificationTime(new Date());
        tkhd.setTrackId(track.getTrackMetaData().getTrackId());
        tkhd.setVolume(track.getTrackMetaData().getVolume());

        trackBox.addBox(tkhd);

        trackBox.addBox(createEdts(track, movie));

        MediaBox mdia = new MediaBox();
        trackBox.addBox(mdia);
        MediaHeaderBox mdhd = new MediaHeaderBox();
        mdhd.setCreationTime(track.getTrackMetaData().getCreationTime());
        mdhd.setDuration(track.getDuration());
        mdhd.setTimescale(track.getTrackMetaData().getTimescale());
        mdhd.setLanguage(track.getTrackMetaData().getLanguage());
        mdia.addBox(mdhd);
        HandlerBox hdlr = new HandlerBox();
        mdia.addBox(hdlr);

        hdlr.setHandlerType(track.getHandler());

        MediaInformationBox minf = new MediaInformationBox();
        if (track.getHandler().equals("vide")) {
            minf.addBox(new VideoMediaHeaderBox());
        } else if (track.getHandler().equals("soun")) {
            minf.addBox(new SoundMediaHeaderBox());
        } else if (track.getHandler().equals("text")) {
            minf.addBox(new NullMediaHeaderBox());
        } else if (track.getHandler().equals("subt")) {
            minf.addBox(new SubtitleMediaHeaderBox());
        } else if (track.getHandler().equals("hint")) {
            minf.addBox(new HintMediaHeaderBox());
        } else if (track.getHandler().equals("sbtl")) {
            minf.addBox(new NullMediaHeaderBox());
        }

        // dinf: all these three boxes tell us is that the actual
        // data is in the current file and not somewhere external
        DataInformationBox dinf = new DataInformationBox();
        DataReferenceBox dref = new DataReferenceBox();
        dinf.addBox(dref);
        DataEntryUrlBox url = new DataEntryUrlBox();
        url.setFlags(1);
        dref.addBox(url);
        minf.addBox(dinf);
        //

        Box stbl = createStbl(track, movie, chunks);
        minf.addBox(stbl);
        mdia.addBox(minf);
        LOG.logDebug("done with trak for track_" + track.getTrackMetaData().getTrackId());
        return trackBox;
    }

    protected Box createEdts(Track track, Movie movie) {
        if (track.getEdits() != null && track.getEdits().size() > 0) {
            EditListBox elst = new EditListBox();
            elst.setVersion(0); // quicktime won't play file when version = 1
            List<EditListBox.Entry> entries = new ArrayList<EditListBox.Entry>();

            for (Edit edit : track.getEdits()) {
                entries.add(new EditListBox.Entry(elst,
                        Math.round(edit.getSegmentDuration() * movie.getTimescale()),
                        edit.getMediaTime() * track.getTrackMetaData().getTimescale() / edit.getTimeScale(),
                        edit.getMediaRate()));
            }

            elst.setEntries(entries);
            EditBox edts = new EditBox();
            edts.addBox(elst);
            return edts;
        } else {
            return null;
        }
    }

    protected Box createStbl(Track track, Movie movie, Map<Track, int[]> chunks) {
        SampleTableBox stbl = new SampleTableBox();

        createStsd(track, stbl);
        createStts(track, stbl);
        createCtts(track, stbl);
        createStss(track, stbl);
        createSdtp(track, stbl);
        createStsc(track, chunks, stbl);
        createStsz(track, stbl);
        createStco(track, movie, chunks, stbl);


        Map<String, List<GroupEntry>> groupEntryFamilies = new HashMap<String, List<GroupEntry>>();
        for (Map.Entry<GroupEntry, long[]> sg : track.getSampleGroups().entrySet()) {
            String type = sg.getKey().getType();
            List<GroupEntry> groupEntries = groupEntryFamilies.get(type);
            if (groupEntries == null) {
                groupEntries = new ArrayList<GroupEntry>();
                groupEntryFamilies.put(type, groupEntries);
            }
            groupEntries.add(sg.getKey());
        }
        for (Map.Entry<String, List<GroupEntry>> sg : groupEntryFamilies.entrySet()) {
            SampleGroupDescriptionBox sgdb = new SampleGroupDescriptionBox();
            String type = sg.getKey();
            sgdb.setGroupingType(type);
            sgdb.setGroupEntries(sg.getValue());
            SampleToGroupBox sbgp = new SampleToGroupBox();
            sbgp.setGroupingType(type);
            SampleToGroupBox.Entry last = null;
            for (int i = 0; i < track.getSamples().size(); i++) {
                int index = 0;
                for (int j = 0; j < sg.getValue().size(); j++) {
                    GroupEntry groupEntry = sg.getValue().get(j);
                    long[] sampleNums = track.getSampleGroups().get(groupEntry);
                    if (Arrays.binarySearch(sampleNums, i) >= 0) {
                        index = j + 1;
                    }
                }
                if (last == null || last.getGroupDescriptionIndex() != index) {
                    last = new SampleToGroupBox.Entry(1, index);
                    sbgp.getEntries().add(last);
                } else {
                    last.setSampleCount(last.getSampleCount() + 1);
                }
            }
            stbl.addBox(sgdb);
            stbl.addBox(sbgp);
        }

        if (track instanceof CencEncryptedTrack) {
            createCencBoxes((CencEncryptedTrack) track, stbl, chunks.get(track));
        }
        createSubs(track, stbl);
        LOG.logDebug("done with stbl for track_" + track.getTrackMetaData().getTrackId());
        return stbl;
    }

    protected void createSubs(Track track, SampleTableBox stbl) {
        if (track.getSubsampleInformationBox() != null) {
            stbl.addBox(track.getSubsampleInformationBox());
        }
    }

    protected void createCencBoxes(CencEncryptedTrack track, SampleTableBox stbl, int[] chunkSizes) {

        SampleAuxiliaryInformationSizesBox saiz = new SampleAuxiliaryInformationSizesBox();

        saiz.setAuxInfoType("cenc");
        saiz.setFlags(1);
        List<CencSampleAuxiliaryDataFormat> sampleEncryptionEntries = track.getSampleEncryptionEntries();
        if (track.hasSubSampleEncryption()) {
            short[] sizes = new short[sampleEncryptionEntries.size()];
            for (int i = 0; i < sizes.length; i++) {
                sizes[i] = (short) sampleEncryptionEntries.get(i).getSize();
            }
            saiz.setSampleInfoSizes(sizes);
        } else {
            saiz.setDefaultSampleInfoSize(8); // 8 bytes iv
            saiz.setSampleCount(track.getSamples().size());
        }

        SampleAuxiliaryInformationOffsetsBox saio = new SampleAuxiliaryInformationOffsetsBox();
        SampleEncryptionBox senc = new SampleEncryptionBox();
        senc.setSubSampleEncryption(track.hasSubSampleEncryption());
        senc.setEntries(sampleEncryptionEntries);

        long offset = senc.getOffsetToFirstIV();
        int index = 0;
        long[] offsets = new long[chunkSizes.length];


        for (int i = 0; i < chunkSizes.length; i++) {
            offsets[i] = offset;
            for (int j = 0; j < chunkSizes[i]; j++) {
                offset += sampleEncryptionEntries.get(index++).getSize();
            }
        }
        saio.setOffsets(offsets);

        stbl.addBox(saiz);
        stbl.addBox(saio);
        stbl.addBox(senc);
        sampleAuxiliaryInformationOffsetsBoxes.add(saio);


    }

    protected void createStsd(Track track, SampleTableBox stbl) {
        stbl.addBox(track.getSampleDescriptionBox());
    }

    protected void createStco(Track targetTrack, Movie movie, Map<Track, int[]> chunks, SampleTableBox stbl) {
        if (chunkOffsetBoxes.get(targetTrack) == null) {
            // The ChunkOffsetBox we create here is just a stub
            // since we haven't created the whole structure we can't tell where the
            // first chunk starts (mdat box). So I just let the chunk offset
            // start at zero and I will add the mdat offset later.

            long offset = 0;
            // all tracks have the same number of chunks
            LOG.logDebug("Calculating chunk offsets for track_" + targetTrack.getTrackMetaData().getTrackId());

            List<Track> tracks = new ArrayList<Track>(chunks.keySet());
            Collections.sort(tracks, new Comparator<Track>() {
                public int compare(Track o1, Track o2) {
                    return l2i(o1.getTrackMetaData().getTrackId() - o2.getTrackMetaData().getTrackId());
                }
            });
            Map<Track, Integer> trackToChunk = new HashMap<Track, Integer>();
            Map<Track, Integer> trackToSample = new HashMap<Track, Integer>();
            Map<Track, Double> trackToTime = new HashMap<Track, Double>();
            for (Track track : tracks) {
                trackToChunk.put(track, 0);
                trackToSample.put(track, 0);
                trackToTime.put(track, 0.0);
                chunkOffsetBoxes.put(track, new StaticChunkOffsetBox());
            }

            while (true) {
                Track nextChunksTrack = null;
                for (Track track : tracks) {
                    // This always chooses the least progressed track
                    if ((nextChunksTrack == null || trackToTime.get(track) < trackToTime.get(nextChunksTrack)) &&
                            // either first OR track's next chunk's starttime is smaller than nextTrack's next chunks starttime
                            // AND their need to be chunks left!
                            (trackToChunk.get(track) < chunks.get(track).length)) {
                        nextChunksTrack = track;
                    }
                }
                if (nextChunksTrack == null) {
                    break; // no next
                }
                // found the next one
                ChunkOffsetBox chunkOffsetBox = chunkOffsetBoxes.get(nextChunksTrack);
                chunkOffsetBox.setChunkOffsets(Mp4Arrays.copyOfAndAppend(chunkOffsetBox.getChunkOffsets(), offset));

                int nextChunksIndex = trackToChunk.get(nextChunksTrack);

                int numberOfSampleInNextChunk = chunks.get(nextChunksTrack)[nextChunksIndex];
                int startSample = trackToSample.get(nextChunksTrack);
                double time = trackToTime.get(nextChunksTrack);

                long[] durs = nextChunksTrack.getSampleDurations();
                for (int j = startSample; j < startSample + numberOfSampleInNextChunk; j++) {
                    offset += track2SampleSizes.get(nextChunksTrack)[j];
                    time += (double) durs[j] / nextChunksTrack.getTrackMetaData().getTimescale();
                }
                trackToChunk.put(nextChunksTrack, nextChunksIndex + 1);
                trackToSample.put(nextChunksTrack, startSample + numberOfSampleInNextChunk);
                trackToTime.put(nextChunksTrack, time);
            }

        }

        stbl.addBox(chunkOffsetBoxes.get(targetTrack));
    }

    protected void createStsz(Track track, SampleTableBox stbl) {
        SampleSizeBox stsz = new SampleSizeBox();
        stsz.setSampleSizes(track2SampleSizes.get(track));

        stbl.addBox(stsz);
    }

    protected void createStsc(Track track, Map<Track, int[]> chunks, SampleTableBox stbl) {
        int[] tracksChunkSizes = chunks.get(track);

        SampleToChunkBox stsc = new SampleToChunkBox();
        stsc.setEntries(new LinkedList<SampleToChunkBox.Entry>());
        long lastChunkSize = Integer.MIN_VALUE; // to be sure the first chunks hasn't got the same size
        for (int i = 0; i < tracksChunkSizes.length; i++) {
            // The sample description index references the sample description box
            // that describes the samples of this chunk. My Tracks cannot have more
            // than one sample description box. Therefore 1 is always right
            // the first chunk has the number '1'
            if (lastChunkSize != tracksChunkSizes[i]) {
                stsc.getEntries().add(new SampleToChunkBox.Entry(i + 1, tracksChunkSizes[i], 1));
                lastChunkSize = tracksChunkSizes[i];
            }
        }
        stbl.addBox(stsc);
    }

    protected void createSdtp(Track track, SampleTableBox stbl) {
        if (track.getSampleDependencies() != null && !track.getSampleDependencies().isEmpty()) {
            SampleDependencyTypeBox sdtp = new SampleDependencyTypeBox();
            sdtp.setEntries(track.getSampleDependencies());
            stbl.addBox(sdtp);
        }
    }

    protected void createStss(Track track, SampleTableBox stbl) {
        long[] syncSamples = track.getSyncSamples();
        if (syncSamples != null && syncSamples.length > 0) {
            SyncSampleBox stss = new SyncSampleBox();
            stss.setSampleNumber(syncSamples);
            stbl.addBox(stss);
        }
    }

    protected void createCtts(Track track, SampleTableBox stbl) {
        List<CompositionTimeToSample.Entry> compositionTimeToSampleEntries = track.getCompositionTimeEntries();
        if (compositionTimeToSampleEntries != null && !compositionTimeToSampleEntries.isEmpty()) {
            CompositionTimeToSample ctts = new CompositionTimeToSample();
            ctts.setEntries(compositionTimeToSampleEntries);
            stbl.addBox(ctts);
        }
    }

    protected void createStts(Track track, SampleTableBox stbl) {
        TimeToSampleBox.Entry lastEntry = null;
        List<TimeToSampleBox.Entry> entries = new ArrayList<TimeToSampleBox.Entry>();

        for (long delta : track.getSampleDurations()) {
            if (lastEntry != null && lastEntry.getDelta() == delta) {
                lastEntry.setCount(lastEntry.getCount() + 1);
            } else {
                lastEntry = new TimeToSampleBox.Entry(1, delta);
                entries.add(lastEntry);
            }

        }
        TimeToSampleBox stts = new TimeToSampleBox();
        stts.setEntries(entries);
        stbl.addBox(stts);
    }

    /**
     * Gets the chunk sizes for the given track.
     *
     * @param track the track we are talking about
     * @return the size of each chunk in number of samples
     */
    int[] getChunkSizes(Track track) {

        long[] referenceChunkStarts = fragmenter.sampleNumbers(track);
        int[] chunkSizes = new int[referenceChunkStarts.length];


        for (int i = 0; i < referenceChunkStarts.length; i++) {
            long start = referenceChunkStarts[i] - 1;
            long end;
            if (referenceChunkStarts.length == i + 1) {
                end = track.getSamples().size();
            } else {
                end = referenceChunkStarts[i + 1] - 1;
            }

            chunkSizes[i] = l2i(end - start);
        }
        assert DefaultMp4Builder1.this.track2Sample.get(track).size() == sum(chunkSizes) : "The number of samples and the sum of all chunk lengths must be equal";
        return chunkSizes;


    }

    public long getTimescale(Movie movie) {

        long timescale = movie.getTracks().iterator().next().getTrackMetaData().getTimescale();
        for (Track track : movie.getTracks()) {
            timescale = lcm(timescale, track.getTrackMetaData().getTimescale());
        }
        return timescale;
    }

    private class InterleaveChunkMdat implements Box {
        List<Track> tracks;
        List<List<Sample>> chunkList = new ArrayList<List<Sample>>();
        Container parent;

        long contentSize;

        private InterleaveChunkMdat(Movie movie, Map<Track, int[]> chunks, long contentSize) {
            this.contentSize = contentSize;
            this.tracks = movie.getTracks();
            List<Track> tracks = new ArrayList<Track>(chunks.keySet());
            Collections.sort(tracks, new Comparator<Track>() {
                public int compare(Track o1, Track o2) {
                    return l2i(o1.getTrackMetaData().getTrackId() - o2.getTrackMetaData().getTrackId());
                }
            });
            Map<Track, Integer> trackToChunk = new HashMap<Track, Integer>();
            Map<Track, Integer> trackToSample = new HashMap<Track, Integer>();
            Map<Track, Double> trackToTime = new HashMap<Track, Double>();
            for (Track track : tracks) {
                trackToChunk.put(track, 0);
                trackToSample.put(track, 0);
                trackToTime.put(track, 0.0);
            }

            while (true) {
                Track nextChunksTrack = null;
                for (Track track : tracks) {
                    if ((nextChunksTrack == null || trackToTime.get(track) < trackToTime.get(nextChunksTrack)) &&
                            // either first OR track's next chunk's starttime is smaller than nextTrack's next chunks starttime
                            // AND their need to be chunks left!
                            (trackToChunk.get(track) < chunks.get(track).length)) {
                        nextChunksTrack = track;
                    }
                }
                if (nextChunksTrack == null) {
                    break;
                }
                // found the next one

                int nextChunksIndex = trackToChunk.get(nextChunksTrack);
                int numberOfSampleInNextChunk = chunks.get(nextChunksTrack)[nextChunksIndex];
                int startSample = trackToSample.get(nextChunksTrack);
                double time = trackToTime.get(nextChunksTrack);
                for (int j = startSample; j < startSample + numberOfSampleInNextChunk; j++) {
                    time += (double) nextChunksTrack.getSampleDurations()[j] / nextChunksTrack.getTrackMetaData().getTimescale();
                }
                chunkList.add(nextChunksTrack.getSamples().subList(startSample, startSample + numberOfSampleInNextChunk));

                trackToChunk.put(nextChunksTrack, nextChunksIndex + 1);
                trackToSample.put(nextChunksTrack, startSample + numberOfSampleInNextChunk);
                trackToTime.put(nextChunksTrack, time);
            }


        }

        public Container getParent() {
            return parent;
        }

        public void setParent(Container parent) {
            this.parent = parent;
        }

        public long getOffset() {
            throw new RuntimeException("Doesn't have any meaning for programmatically created boxes");
        }

        public void parse(DataSource dataSource, ByteBuffer header, long contentSize, BoxParser boxParser) throws IOException {
        }

        public long getDataOffset() {
            Object b = this;
            long offset = 16;
            while (b instanceof Box) {
                for (Box box : ((Box) b).getParent().getBoxes()) {
                    if (b == box) {
                        break;
                    }
                    offset += box.getSize();
                }
                b = ((Box) b).getParent();
            }
            return offset;
        }


        public String getType() {
            return "mdat";
        }

        public long getSize() {
            return 16 + contentSize;
        }

        private boolean isSmallBox(long contentSize) {
            return (contentSize + 8) < 4294967296L;
        }


        public void getBox(WritableByteChannel writableByteChannel) throws IOException {
            ByteBuffer bb = ByteBuffer.allocate(16);
            long size = getSize();
            if (isSmallBox(size)) {
                IsoTypeWriter.writeUInt32(bb, size);
            } else {
                IsoTypeWriter.writeUInt32(bb, 1);
            }
            bb.put(IsoFile.fourCCtoBytes("mdat"));
            if (isSmallBox(size)) {
                bb.put(new byte[8]);
            } else {
                IsoTypeWriter.writeUInt64(bb, size);
            }
            bb.rewind();
            writableByteChannel.write(bb);
            long writtenBytes = 0;
            long writtenMegaBytes = 0;

            String totalsize=getFileSize(contentSize);

                   try{
                        String persntage=((writtenBytes*100) / contentSize) + "%";
                        shownotification(getFileSize(writtenBytes),totalsize,persntage);
                    }
                    catch (Exception a)
                    {
                       // Log.w("Suhi",Log.getStackTraceString(a));
                    }


            for (List<Sample> samples : chunkList) {
                for (Sample sample : samples) {
                    sample.writeTo(writableByteChannel);
                    writtenBytes += sample.getSize();

//                    if (writtenBytes > 1024 *1024) {
//
//                        writtenBytes -= 1024 * 1024;
//                        writtenMegaBytes++;
//                        //LOG.logDebug("Ezuthiyatha " + writtenMegaBytes + "MB");
//                    }
                }
                      try{
                          String persntage=((writtenBytes*100) / contentSize) + "%";
                           shownotification(getFileSize(writtenBytes),totalsize,persntage);
                        }
                        catch (Exception a)
                        {
                            Log.w("Suhi",Log.getStackTraceString(a));
                        }

                      if(contentSize<=writtenBytes)
                      {
                          clearNotification(path,ctime);
                      }
            }


        }

    }

    public void shownotification(String currentsize,String totalsize,String persentage)
    {
        final RemoteViews contentView = new RemoteViews("com.downly_app", R.layout.custom_list_notidownloads_special);
        contentView.setTextViewText(R.id.title, "Merging , Please wait...");
        contentView.setTextViewText(R.id.title1, currentsize+" / "+totalsize+" , "+persentage);

        Intent intent1 = new Intent();
        final PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent1, 0);

        if (Build.VERSION.SDK_INT < 26) {
            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
                    .setSmallIcon(R.drawable.logo_small)
                    .setContentIntent(pendingIntent)
                    .setVibrate(new long[]{0L})
                    .setSound(null)
                    .setContent(contentView);

            Notification notification = mBuilder.build();
            notification.flags = notification.flags
                    | Notification.FLAG_ONGOING_EVENT;
            final NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(1237, notification);
        }
        else
        {

            CharSequence name = "DownlyNew";
            String description = "DownlyNotiNew";
            int importance = NotificationManager.IMPORTANCE_LOW;
            NotificationChannel channel = new NotificationChannel("DownlychannelNew", name, importance);
            channel.setDescription(description);
            channel.setVibrationPattern(new long[]{ 0 });
            channel.enableVibration(false);
            channel.setSound(null, null);
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, "DownlychannelNew")
                    .setSmallIcon(R.drawable.logo_small)
                    .setContentIntent(pendingIntent)
                    .setContent(contentView)
                    .setPriority(NotificationCompat.PRIORITY_LOW);

            Notification notification = mBuilder.build();
            notification.flags = notification.flags
                    | Notification.FLAG_ONGOING_EVENT;
            notification.flags |= Notification.FLAG_AUTO_CANCEL;
            notificationManager.notify(1237, notification);
        }

    }
    public void clearNotification(String path,String ctime) {
        NotificationManager notificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(1237);

        File f=new File(path);
        if(f.exists())
        {

            if(f.exists())
            {
                String downname="YTB_"+ctime+".mp4";
                String downnamedel="YT_"+ctime+".m4a";
                addtoGallery(f);

                db.delete_file_bypath(downnamedel);

                db.updatedownpathbyctime(ctime + "", path);

                final RemoteViews contentView = new RemoteViews("com.downly_app", R.layout.custom_list_notidownloads);
                contentView.setTextViewText(R.id.title, "Download Complete");
                contentView.setTextViewText(R.id.title1, downname);

                if (downname.contains("FB")) {
                    contentView.setImageViewResource(R.id.image1, R.drawable.new_fblogo);
                } else if (downname.contains("INSTA")) {
                    contentView.setImageViewResource(R.id.image1, R.drawable.new_instagram);
                } else if (downname.contains("TIKS")) {
                    contentView.setImageViewResource(R.id.image1, R.drawable.new_tiktok);
                } else if (downname.contains("SC")) {
                    contentView.setImageViewResource(R.id.image1, R.drawable.new_sharechat);
                } else if (downname.contains("TW")) {
                    contentView.setImageViewResource(R.id.image1, R.drawable.new_twitter);
                } else if (downname.contains("PIN")) {
                    contentView.setImageViewResource(R.id.image1, R.drawable.new_pintrest);
                } else if (downname.contains("YTB")) {
                    contentView.setImageViewResource(R.id.image1, R.drawable.new_youtube);
                } else {
                    contentView.setImageViewResource(R.id.image1, R.drawable.applogo);
                }

                Intent intent1 = new Intent();
                intent1.setAction(Intent.ACTION_VIEW);
                intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                Uri apkURI = FileProvider.getUriForFile(
                        context.getApplicationContext(),
                        "com.downly_app.provider", f);
                String mimtype = "";
                if (f.getName().endsWith(".mp4") || f.getName().endsWith(".MP4")) {
                    mimtype = "video/*";
                } else if (f.getName().endsWith(".jpg") || f.getName().endsWith(".jpeg") || f.getName().endsWith(".png") || f.getName().endsWith(".gif") || f.getName().endsWith(".JPG") || f.getName().endsWith(".JPEG") || f.getName().endsWith(".PNG") || f.getName().endsWith(".GIF")) {
                    mimtype = "image/*";
                } else if (f.getName().endsWith(".mp3") || f.getName().endsWith(".MP3")) {
                    mimtype = "audio/*";
                }
                intent1.setDataAndType(apkURI, mimtype);
                intent1.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                final PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent1, PendingIntent.FLAG_CANCEL_CURRENT);


                if (Build.VERSION.SDK_INT < 26) {
                    NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
                            .setSmallIcon(R.drawable.logo_small)
                            .setContentIntent(pendingIntent)
                            .setContent(contentView);

                    Notification notification = mBuilder.build();
                    notification.flags |= Notification.FLAG_AUTO_CANCEL;
                    notification.defaults |= Notification.DEFAULT_SOUND;
                    notification.defaults |= Notification.DEFAULT_VIBRATE;
                    final NotificationManager notificationManager1 = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                    notificationManager1.notify(Notification_ID.getID(), notification);
                } else {

                    CharSequence name = "Downly";
                    String description = "DownlyNoti";
                    int importance = NotificationManager.IMPORTANCE_DEFAULT;
                    NotificationChannel channel = new NotificationChannel("Downlychannel", name, importance);
                    channel.setDescription(description);
                    NotificationManager notificationManager1 = context.getSystemService(NotificationManager.class);
                    notificationManager.createNotificationChannel(channel);
                    NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, "Downlychannel")
                            .setSmallIcon(R.drawable.logo_small)
                            .setContentIntent(pendingIntent)
                            .setContent(contentView)
                            .setPriority(NotificationCompat.PRIORITY_DEFAULT);

                    Notification notification = mBuilder.build();
                    notification.flags |= Notification.FLAG_AUTO_CANCEL;
                    notificationManager1.notify(Notification_ID.getID(), notification);
                }

            }



        }

    }

    public void addtoGallery(File file ) {
        if(file.getName().endsWith(".jpg") || file.getName().endsWith(".JPG"))
        {
            ContentValues values = new ContentValues();
            values.put(MediaStore.Images.Media.DATA, file.getAbsolutePath());
            values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpg"); // or image/png
            context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        }
        else if(file.getName().endsWith(".jpeg") || file.getName().endsWith(".JPEG"))
        {
            ContentValues values = new ContentValues();
            values.put(MediaStore.Images.Media.DATA, file.getAbsolutePath());
            values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg"); // or image/png
            context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        }
        else if(file.getName().endsWith(".png") || file.getName().endsWith(".PNG"))
        {
            ContentValues values = new ContentValues();
            values.put(MediaStore.Images.Media.DATA, file.getAbsolutePath());
            values.put(MediaStore.Images.Media.MIME_TYPE, "image/png"); // or image/png
            context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        }
        else if(file.getName().endsWith(".mp4") || file.getName().endsWith(".MP4"))
        {
            ContentValues values = new ContentValues();
            values.put(MediaStore.Video.Media.DATA, file.getAbsolutePath());
            values.put(MediaStore.Video.Media.MIME_TYPE, "video/mp4"); // or image/png
            context.getContentResolver().insert(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, values);
        }
    }

    public static String getFileSize(long size) {
        if (size <= 0)
            return "0";

        final String[] units = new String[] { "B", "KB", "MB", "GB", "TB" };
        int digitGroups = (int) (Math.log10(size) / Math.log10(1024));

        return new DecimalFormat("#,##0.#").format(size / Math.pow(1024, digitGroups)) + " " + units[digitGroups];
    }

}
