package glide.versioning;

import java.io.Serializable;

public class Version implements Serializable{
	
	private static final long serialVersionUID = 8734692561296056494L;
	
	private int build;
	private String version;
	
	public Version(int build, String version)
	{
		this.build = build;
		this.version = version;
	}
	
	public int getBuild(){
		return this.build;
	}
	
	public String getVersion()
	{
		return this.version;
	}
	
	public static String getAppVersion(){
		return "v1.0.79b";
	}
	
	public static int getAppBuild(){
		return 624;
	}
}
