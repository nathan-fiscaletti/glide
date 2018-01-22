package glide.game;

import glide.game.drawables.BackgroundDrawable;
import glide.game.drawables.LogoDrawable;
import glide.game.screens.MainMenu;
import glide.game.sounds.GlideSounds;
import glide.game.versioning.Updater;
import glide.game.versioning.Version;

import jtwod.engine.Engine;
import jtwod.engine.graphics.SpriteSheet;
import jtwod.engine.graphics.Texture;
import jtwod.engine.metrics.Dimensions;

import java.awt.Color;
import java.awt.LayoutManager;

public final class GlideEngine extends Engine {
    /* Game Properties */
    public final String version = Version.getAppVersion() + " [Build: " + Version.getAppBuild() + "]";

    /* Used to render the logo out on certain screens */
    public LogoDrawable logoRenderer;
    public BackgroundDrawable backgroundRenderer;

    public GlideSounds sounds;
    public Cheats cheats;
    public Difficulty difficulty;
    public Updater updater;
    public boolean enableMusic = true;

    /* Loading Status */
    public static LayoutManager correctLayout = null;

    public GlideEngine()
    {
        // Set the title, icon and base textures for the game.
        // Set the bounds to 800 width with a 12x9 aspect ratio.
        super(
            "Glide",
            new Dimensions (
                800,
                new Dimensions (
                    12, 9
                )
            ),
            "/images/icon.png"
        );

        // Initialize our two global drawables.
        this.logoRenderer = new LogoDrawable("/images/logo.png", this);
        this.backgroundRenderer = new BackgroundDrawable("/images/mm_b.png", this);;

        // Initialize our sounds and cheats.
        this.sounds = new GlideSounds();
        this.cheats = new Cheats();

        // Initialize our difficulty with a default of Expert.
        this.difficulty = Difficulty.Expert;

        // Initialize the Updater object to check for an update.
        try {
            updater = new Updater();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public void loadTextures()
    {
        SpriteSheet spriteSheet = new SpriteSheet(new Texture("/images/sprite_sheet.png"), 32, 32);

        /* Enemies */
        this.getTextureGroup().addTexture("enemy", spriteSheet.grabSprite(4,1));
        this.getTextureGroup().addTexture("enemy2", spriteSheet.grabSprite(4, 2));
        this.getTextureGroup().addTexture("enemy3", spriteSheet.grabSprite(4, 3));
        this.getTextureGroup().addTexture("bossprotector", spriteSheet.grabSprite(1, 5));

        /* Bullets */
        this.getTextureGroup().addTexture("bullet", spriteSheet.grabSprite(2, 1));
        this.getTextureGroup().addTexture("enemybullet", spriteSheet.grabSprite(2, 2));
        this.getTextureGroup().addTexture("enemybulletleft", spriteSheet.grabSprite(6, 4));
        this.getTextureGroup().addTexture("enemybulletright", spriteSheet.grabSprite(7, 4));
        this.getTextureGroup().addTexture("mdbullet", spriteSheet.grabSprite(2, 3));


        /* Animations */
        this.getTextureGroup().addTexture("des1", spriteSheet.grabSprite(1, 8));
        this.getTextureGroup().addTexture("des2", spriteSheet.grabSprite(2, 8));
        this.getTextureGroup().addTexture("des3", spriteSheet.grabSprite(3, 8));

        /* Player States */
        this.getTextureGroup().addTexture("player", spriteSheet.grabSprite(1, 1));
        this.getTextureGroup().addTexture("player2", spriteSheet.grabSprite(1, 2));
        this.getTextureGroup().addTexture("playerhurt", spriteSheet.grabSprite(1, 3));
        this.getTextureGroup().addTexture("plasmaplayer", spriteSheet.grabSprite(1, 4));

        /* GUI */
        this.getTextureGroup().addTexture("max", spriteSheet.grabSprite(6, 1));
        this.getTextureGroup().addTexture("max_cod", spriteSheet.grabSprite(6, 2));
        this.getTextureGroup().addTexture("bombsp", spriteSheet.grabSprite(5, 6));
        this.getTextureGroup().addTexture("mdppickup", spriteSheet.grabSprite(5, 5));
        this.getTextureGroup().addTexture("cod", spriteSheet.grabSprite(4, 5));

        /* Drops */
        this.getTextureGroup().addTexture("beam", spriteSheet.grabSprite(5, 2));
        this.getTextureGroup().addTexture("plasma", spriteSheet.grabSprite(5, 4));
        this.getTextureGroup().addTexture("mdppickup", spriteSheet.grabSprite(5, 5));
        this.getTextureGroup().addTexture("diamond", spriteSheet.grabSprite(5, 3));
        this.getTextureGroup().addTexture("diamond2", spriteSheet.grabSprite(6, 3));
        this.getTextureGroup().addTexture("diamond3", spriteSheet.grabSprite(7, 3));
        this.getTextureGroup().addTexture("healthpack", spriteSheet.grabSprite(5, 1));
        this.getTextureGroup().addTexture("cod_pickup", spriteSheet.grabSprite(4, 4));

        /* Health Bar */
        this.getTextureGroup().addTexture("healthbar1", spriteSheet.grabSprite(7, 1));
        this.getTextureGroup().addTexture("healthbar2", spriteSheet.grabSprite(7, 2));
        this.getTextureGroup().addTexture("healthbar3", spriteSheet.grabSprite(8, 1));
        this.getTextureGroup().addTexture("healthbar4", spriteSheet.grabSprite(8, 2));
        this.getTextureGroup().addTexture("healthbar5", spriteSheet.grabSprite(8, 3));
        this.getTextureGroup().addTexture("healthbar6", spriteSheet.grabSprite(8, 4));
        this.getTextureGroup().addTexture("healthbar7", spriteSheet.grabSprite(8, 5));
        this.getTextureGroup().addTexture("healthbar8", spriteSheet.grabSprite(8, 6));

        /* Power Bar */
        this.getTextureGroup().addTexture("powerbar1", spriteSheet.grabSprite(3,1));
        this.getTextureGroup().addTexture("powerbar2", spriteSheet.grabSprite(3,2));
        this.getTextureGroup().addTexture("powerbar3", spriteSheet.grabSprite(3,3));
        this.getTextureGroup().addTexture("powerbar4", spriteSheet.grabSprite(3,4));
        this.getTextureGroup().addTexture("powerbar5", spriteSheet.grabSprite(3,5));

        //this.getTextureGroup().addTexture("grayscale_sheild = ImageFunctions.grayScale(plasma);
        //this.getTextureGroup().addTexture("grayscale_beam = ImageFunctions.grayScale(beam);

        /* Meteors */
        this.getTextureGroup().addTexture("meteor1", spriteSheet.grabSprite(1, 7));
        this.getTextureGroup().addTexture("meteor2", spriteSheet.grabSprite(2, 7));
        this.getTextureGroup().addTexture("meteor3", spriteSheet.grabSprite(3, 7));
        this.getTextureGroup().addTexture("meteor4", spriteSheet.grabSprite(1, 6));
        this.getTextureGroup().addTexture("meteor5", spriteSheet.grabSprite(2, 6));
        this.getTextureGroup().addTexture("meteor6", spriteSheet.grabSprite(3, 6));

        /* Small Meteors */
        this.getTextureGroup().addTexture("smallmeteor1", spriteSheet.grabSprite(4, 7));
        this.getTextureGroup().addTexture("smallmeteor2", spriteSheet.grabSprite(5, 7));
        this.getTextureGroup().addTexture("smallmeteor3", spriteSheet.grabSprite(4, 6));
        this.getTextureGroup().addTexture("smallmeteor4", spriteSheet.grabSprite(6, 7));
        this.getTextureGroup().addTexture("smallmeteor5", spriteSheet.grabSprite(7, 7));
        this.getTextureGroup().addTexture("smallmeteor6", spriteSheet.grabSprite(8, 7));
    }

    @Override
    public void onEngineStart() {
        this.addGlobalRenderer(this.backgroundRenderer);
        this.addGlobalRenderer(this.logoRenderer);
        sounds.loadSounds();

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
