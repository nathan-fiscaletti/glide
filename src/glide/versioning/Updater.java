package glide.versioning;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.UnknownHostException;

public class Updater {
	private URL online;
	private Version onlineVersion;
	
	private boolean needsUpdate = false;
	public Updater(URL url) throws IOException, ClassNotFoundException{
		this.online = url;
		init();
	}
	
	private void init() throws IOException, ClassNotFoundException{
		if(internetCheck()){
			String fname = rs(10) + ".v";
			FileOutputStream fos = new FileOutputStream(fname, false);
			BufferedOutputStream out = new BufferedOutputStream(fos);
			BufferedInputStream in = new BufferedInputStream(online.openStream());
			byte data[] = new byte[1024];
			int count;
			while((count = in.read(data,0,1024)) != -1)
			{
		        out.write(data, 0, count);
		    }
		    in.close();
		    out.close();
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
			fos.close();
			new File(fname).delete();
		}
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
	public static boolean internetCheck()
    {
        try {
            URL url = new URL("http://www.visualisticstudios.com/");
            HttpURLConnection urlConnect = (HttpURLConnection)url.openConnection();
            @SuppressWarnings("unused")
			Object objData = urlConnect.getContent();
        } catch (UnknownHostException e) {
            return false;
        }
        catch (IOException e) {
            return false;
        }
        return true;
    }
}
