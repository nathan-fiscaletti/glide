package glide.versioning;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.URL;
import java.util.Random;

public class Updater {
	private URL online;
	private Version onlineVersion;
	
	private boolean needsUpdate = false;
	private String updateURL;
	public Updater(URL url) throws IOException, ClassNotFoundException{
		this.online = url;
		init();
	}
	
	private void init() throws IOException, ClassNotFoundException{
		String fname = rs(10) + ".v";
		BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(fname, false));
		BufferedInputStream in = new BufferedInputStream(online.openStream());
	    byte data[] = new byte[1024];
	    int count;
	    while((count = in.read(data,0,1024)) != -1)
	    {
	        out.write(data, 0, count);
	    }
	    out.close();
	    in.close();
	    FileInputStream fin = new FileInputStream(fname);
		ObjectInputStream ois = new ObjectInputStream(fin);
		Version v = (Version) ois.readObject();
		this.onlineVersion = v;
		if(Version.getAppBuild() < this.onlineVersion.getBuild()){
			this.needsUpdate = true;
		}else{
			this.needsUpdate = false;
		}
		fin.close();
		ois.close();
		new File(fname).delete();
	}
	
	public boolean needsUpdate(){
		return this.needsUpdate;
	}
	
	public Version getLatestVersion(){
		return onlineVersion;
	}
	
	private String rs(int size){
	    String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
	    char[] c = chars.toCharArray();
	    String ret = "";
	    int length = chars.length();
	    for (int i = 0; i < size; i ++){
	        ret += c[ (int) (Math.random() * (length - 1)) ];
	    }
	    return ret;
	}
}
