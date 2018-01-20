package glide.game.versioning;

import java.awt.Color;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Updater {
	public static final boolean checkedForUpdate = false;
	public static final String updateAt = "https://github.com/nathan-fiscaletti/glide/releases";
	public static final String updateUrl = "https://raw.githubusercontent.com/nathan-fiscaletti/glide/master/glide.v";
	
	private URL online;
	private Version onlineVersion;
	
	public String update = "You are running the latest version!";
	public String update2 = "";
	public Color update_color = Color.GREEN;
	
	private boolean needsUpdate = false;
	public Updater() throws Exception{
		this.online = new URL(updateUrl);
		init();
	}
	
	private void init() throws Exception{
		if(internetCheck()){
			try {
				Scanner s = new Scanner(online.openStream());
				int remoteBuildVersion = Integer.parseInt(s.nextLine());
				String remoteVersion = s.nextLine();
				System.out.println("Remote Build: " + remoteBuildVersion);
				onlineVersion = new Version(remoteBuildVersion, remoteVersion);
				s.close();
				if (remoteBuildVersion > Version.getAppBuild()) {
					this.needsUpdate = true;
				}
			} catch (Exception e) {
				System.out.println();
				onlineVersion = null;
				this.needsUpdate = false;
				throw new Exception("Error while reading remote version: " + e.getMessage());
			}
		}
	}
	
	public boolean needsUpdate(){
		return this.needsUpdate;
	}
	
	public Version getLatestVersion(){
		return onlineVersion;
	}
	
	public boolean internetCheck()
    {
        try {
            URL url = new URL("http://www.google.com/");
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
