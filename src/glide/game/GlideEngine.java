package glide.game;

import glide.game.renderers.BackgroundRenderer;
import glide.game.renderers.LogoRenderer;
import glide.game.screens.MainMenu;
import glide.game.sounds.GlideSounds;
import glide.game.versioning.Updater;
import glide.game.versioning.Version;
import two.d.engine.Engine;

import java.awt.Color;
import java.awt.LayoutManager;
import java.io.IOException;

public class GlideEngine extends Engine {
	/* Game Properties */
	public final String version = Version.getAppVersion() + " [Build: " + Version.getAppBuild() + "]";
	
	/* Used to render the logo out on certain screens */
	public LogoRenderer logoRenderer = new LogoRenderer("/images/logo.png", this);
	public BackgroundRenderer backgroundRenderer = new BackgroundRenderer("/images/mm_b.png", this);
	
	public GlideSounds sounds;
	public Cheats cheats;
	public Difficulty difficulty;
	public Updater updater;
	public boolean enableMusic = true;
	
	/* Loading Status */
	public static LayoutManager correctLayout = null;
	
	public GlideEngine() throws IOException
	{
		super("Glide", "/images/icon.png", new GlideTextures());
		this.windowTitle = "Glide";
	}
	
	@Override
	public void startEngine() {
		this.globalRenderers.add(this.backgroundRenderer);
		this.globalRenderers.add(this.logoRenderer);
		
		// Load sounds
		sounds = new GlideSounds();
		sounds.loadSounds();
	
		cheats = new Cheats();
		
		difficulty = Difficulty.Expert;
		
		try {
			updater = new Updater();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if (! Updater.checkedForUpdate) {
			Updater updater = null;
			if(this.updater.internetCheck()){
				try {
					updater = new Updater();
				} catch (Exception e) {
					this.updater.update = "There was an error while checking for update. - Unable to connect to Glide Servers.";
					this.updater.update_color = Color.RED;
				}
		
				if(updater != null && updater.needsUpdate()){
					this.updater.update = "There is an update available for version " + updater.getLatestVersion().getVersion() + " Build " + updater.getLatestVersion().getBuild() + "!";
					this.updater.update2 = "Update at " + Updater.updateAt;
					this.updater.update_color = Color.YELLOW;
				}
			}else{
				this.updater.update = "There was an error while checking for update. - Unable to connect to Glide Servers.";
				this.updater.update_color = Color.RED;
			}
		}

		setScreen(new MainMenu(this));
	}
}
