package glide.versioning;

import java.io.Serializable;

public class Version implements Serializable{
	
	private static final long serialVersionUID = 8734692561296056494L;
	
	private int build;
	private String version;
	private String updateURL;
	
	public Version(String version, int build, String update){
		this.build = build;
		this.version = version;
		this.updateURL = update;
	}
	
	public String getVersion(){
		return version;
	}
	
	public int getBuild(){
		return this.build;
	}
	
	public String getUpdateURL(){
		return this.updateURL;
	}
	
	
	
	public static String getAppVersion(){
		return "v1.0.66b";
	}
	
	public static int getAppBuild(){
		return 481;
	}
}
