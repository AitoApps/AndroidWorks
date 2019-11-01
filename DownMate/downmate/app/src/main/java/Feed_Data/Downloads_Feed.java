package Feed_Data;

public class Downloads_Feed {

	private String pkey,down_url,down_id,down_type,down_path,down_name,new_name;
	public Downloads_Feed() {
	}

	public Downloads_Feed(String pkey, String down_id, String down_name, String new_name, String down_type, String down_path, String down_url) {
		super();
		this.pkey=pkey;
		this.down_id=down_id;
		this.down_type=down_type;
		this.down_path=down_path;
		this.down_url=down_url;
		this.down_name=down_name;
		this.new_name=new_name;
	}

	public String getdown_url() {
		return down_url;
	}

	public void setdown_url(String down_url) {
		this.down_url = down_url;
	}


	public String getdown_type() {
		return down_type;
	}
	public void setdown_type(String down_type) {
		this.down_type=down_type;
	}

	public String getdown_path() {
		return down_path;
	}

	public void setdown_path(String down_path) {
		this.down_path = down_path;
	}

	public String getnew_name() {
		return new_name;
	}
	public void setnew_name(String new_name) {
		this.new_name=new_name;
	}

	public String getpkey() {
		return pkey;
	}
	public void set_pkey(String pkey) {
		this.pkey=pkey;
	}

	public String getdown_id() {
		return down_id;
	}
	public void setdown_id(String down_id) {
		this.down_id=down_id;
	}


	public String getdown_name() {
		return down_name;
	}

	public void set_down_name(String down_name) {
		this.down_name=down_name;
	}
}
