/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */
package hu.nagytom.pd.pixeldungeon.scenes;

import java.util.HashMap;

import hu.nagytom.pd.noosa.BitmapText;
import hu.nagytom.pd.noosa.BitmapTextMultiline;
import hu.nagytom.pd.noosa.Camera;
import hu.nagytom.pd.noosa.Game;
import hu.nagytom.pd.noosa.Group;
import hu.nagytom.pd.noosa.Image;
import hu.nagytom.pd.noosa.audio.Sample;
import hu.nagytom.pd.noosa.particles.BitmaskEmitter;
import hu.nagytom.pd.noosa.particles.Emitter;
import hu.nagytom.pd.noosa.ui.Button;
import hu.nagytom.pd.pixeldungeon.Assets;
import hu.nagytom.pd.pixeldungeon.Badges;
import hu.nagytom.pd.pixeldungeon.Dungeon;
import hu.nagytom.pd.pixeldungeon.GamesInProgress;
import hu.nagytom.pd.pixeldungeon.PixelDungeon;
import hu.nagytom.pd.pixeldungeon.actors.hero.HeroClass;
import hu.nagytom.pd.pixeldungeon.effects.BannerSprites;
import hu.nagytom.pd.pixeldungeon.effects.Speck;
import hu.nagytom.pd.pixeldungeon.effects.BannerSprites.Type;
import hu.nagytom.pd.pixeldungeon.ui.Archs;
import hu.nagytom.pd.pixeldungeon.ui.CheckBox;
import hu.nagytom.pd.pixeldungeon.ui.ExitButton;
import hu.nagytom.pd.pixeldungeon.ui.Icons;
import hu.nagytom.pd.pixeldungeon.ui.RedButton;
import hu.nagytom.pd.pixeldungeon.ui.Window;
import hu.nagytom.pd.pixeldungeon.utils.Utils;
import hu.nagytom.pd.pixeldungeon.windows.WndChallenges;
import hu.nagytom.pd.pixeldungeon.windows.WndClass;
import hu.nagytom.pd.pixeldungeon.windows.WndMessage;
import hu.nagytom.pd.pixeldungeon.windows.WndOptions;
import hu.nagytom.pd.utils.Callback;

public class StartScene extends PixelScene {

    private static final float BUTTON_HEIGHT    = 24;
    private static final float GAP              = 2;

    private static final String TXT_LOAD    = "Load Game";
    private static final String TXT_NEW     = "New Game";
    private static final String TXT_EXTRAS  = "Extras";
    private static final String TXT_ABANDON = "Abandon Game";

    private static final String TXT_ERASE       = "Erase current game";
    private static final String TXT_DPTH_LVL    = "Depth: %d, level: %d";
    private static final String TXT_EXTRAS_ON   = "Extras are turned on";

    private static final String TXT_REALLY  = "Do you really want to abandon this game?";
    private static final String TXT_WARNING = "Your current game progress will be erased.";
    private static final String TXT_YES     = "Yes, abandon this game";
    private static final String TXT_NO      = "No, I changed my mind";

    private static final String TXT_UNLOCK  = "To unlock this character class, slay the 3rd boss with any other class";

    private static final String TXT_WIN_THE_GAME =
        "To unlock God Mode and \"Challenges\", win the game with any character class.";

    private static final float WIDTH_P  = 116;
    private static final float HEIGHT_P = 220;

    private static final float WIDTH_L  = 224;
    private static final float HEIGHT_L = 124;

    private static HashMap<HeroClass, ClassShield> shields = new HashMap<HeroClass, ClassShield>();

    private float buttonX;
    private float buttonY;
    private float buttonWidth;

    private GameButton btnLoad;
    private GameButton btnAbandon;
    private GameButton btnNewGame;
    private GameButton btnExtras;

    private boolean huntressUnlocked;
    private Group unlock;

    public static HeroClass curClass;

    @Override
    public void create() {

        super.create();

        Badges.loadGlobal();

        uiCamera.visible = false;

        int w = Camera.main.width;
        int h = Camera.main.height;

        float width, height;
        if (PixelDungeon.landscape()) {
            width = WIDTH_L;
            height = HEIGHT_L;
        } else {
            width = WIDTH_P;
            height = HEIGHT_P;
        }

        float left = (w - width) / 2;
        float top = (h - height) / 2;
        float bottom = h - top;

        Archs archs = new Archs();
        archs.setSize( w, h );
        add( archs );

        Image title = BannerSprites.get( Type.SELECT_YOUR_HERO );
        title.x = align( (w - title.width()) / 2 );
        title.y = align( top );
        add( title );

        buttonX = left;
        buttonY = bottom - BUTTON_HEIGHT;
        buttonWidth = (Camera.main.width - GAP) / 2 - buttonX;

        btnNewGame = new GameButton( TXT_NEW ) {
            @Override
            protected void onClick() {
                    startNewGame();
            }
        };
        add( btnNewGame );

        btnAbandon = new GameButton(TXT_ABANDON) {
            @Override
            protected void onClick() {
                StartScene.this.add( new WndOptions( TXT_REALLY, TXT_WARNING, TXT_YES, TXT_NO ) {
                    @Override
                    protected void onSelect( int index ) {
                        if (index == 0) {
                            GamesInProgress.delete(StartScene.curClass);
                            Dungeon.deleteGame(StartScene.curClass, true);
                            PixelDungeon.lastClass(StartScene.curClass.ordinal());
                            PixelDungeon.switchNoFade(StartScene.class);
                        }
                    }
                } );
            }
        };
        add(btnAbandon);

        btnExtras = new GameButton(TXT_EXTRAS) {
            @Override
            protected void onClick() {
                StartScene.this.add(new WndExtras());
                /*if (Badges.isUnlocked(Badges.Badge.VICTORY)) {
                    StartScene.this.add(new WndExtras());
                } else {
                    StartScene.this.add(new WndMessage(TXT_WIN_THE_GAME));
                }*/
            }
        };
        add(btnExtras);

        btnLoad = new GameButton( TXT_LOAD ) {
            @Override
            protected void onClick() {
                InterlevelScene.mode = InterlevelScene.Mode.CONTINUE;
                Game.switchScene( InterlevelScene.class );
            }
        };
        add( btnLoad );

        float centralHeight = buttonY - title.y - title.height();

        HeroClass[] classes = {
            HeroClass.WARRIOR, HeroClass.MAGE, HeroClass.ROGUE, HeroClass.HUNTRESS
        };
        for (HeroClass cl : classes) {
            ClassShield shield = new ClassShield( cl );
            shields.put( cl, shield );
            add( shield );
        }
        if (PixelDungeon.landscape()) {
            float shieldW = width / 4;
            float shieldH = Math.min( centralHeight, shieldW );
            top = title.y + title.height + (centralHeight - shieldH) / 2;
            for (int i=0; i < classes.length; i++) {
                ClassShield shield = shields.get( classes[i] );
                shield.setRect( left + i * shieldW, top, shieldW, shieldH );
            }

        } else {
            float shieldW = width / 2;
            float shieldH = Math.min( centralHeight / 2, shieldW * 1.2f );
            top = title.y + title.height() + centralHeight / 2 - shieldH;
            for (int i=0; i < classes.length; i++) {
                ClassShield shield = shields.get( classes[i] );
                shield.setRect(
                    left + (i % 2) * shieldW,
                    top + (i / 2) * shieldH,
                    shieldW, shieldH );
            }
        }

        unlock = new Group();
        add( unlock );

        if (!(huntressUnlocked = Badges.isUnlocked( Badges.Badge.BOSS_SLAIN_3 ))) {

            BitmapTextMultiline text = PixelScene.createMultiline( TXT_UNLOCK, 9 );
            text.maxWidth = (int)width;
            text.measure();

            float pos = (bottom - BUTTON_HEIGHT) + (BUTTON_HEIGHT - text.height()) / 2;
            for (BitmapText line : text.new LineSplitter().split()) {
                line.measure();
                line.hardlight( 0xFFFF00 );
                line.x = PixelScene.align( w / 2 - line.width() / 2 );
                line.y = PixelScene.align( pos );
                unlock.add( line );

                pos += line.height();
            }
        }

        ExitButton btnExit = new ExitButton();
        btnExit.setPos( Camera.main.width - btnExit.width(), 0 );
        add( btnExit );

        curClass = null;
        updateClass( HeroClass.values()[PixelDungeon.lastClass()] );

        fadeIn();

        Badges.loadingListener = new Callback() {
            @Override
            public void call() {
                if (Game.scene() == StartScene.this) {
                    PixelDungeon.switchNoFade( StartScene.class );
                }
            }
        };
    }

    @Override
    public void destroy() {

        Badges.saveGlobal();
        Badges.loadingListener = null;

        super.destroy();
    }

    private void updateClass( HeroClass cl ) {

        if (curClass == cl) {
            add( new WndClass( cl ) );
            return;
        }

        if (curClass != null) {
            shields.get( curClass ).highlight( false );
        }
        shields.get( curClass = cl ).highlight( true );

        if (cl != HeroClass.HUNTRESS || huntressUnlocked) {
            unlock.visible = false;

            GamesInProgress.Info info = GamesInProgress.check(curClass);
            if (info != null) {
                btnExtras.visible = false;
                btnNewGame.visible = false;
                btnLoad.visible = true;
                btnAbandon.visible = true;

                btnLoad.secondary(Utils.format(TXT_DPTH_LVL, info.depth, info.level), info.challenges || info.godMode);
                btnLoad.setRect(buttonX, buttonY, buttonWidth, BUTTON_HEIGHT);
                btnAbandon.secondary(TXT_ERASE, false);
                btnAbandon.setRect(btnLoad.right() + GAP, buttonY, buttonWidth, BUTTON_HEIGHT);
            } else {
                btnAbandon.visible = false;
                btnLoad.visible = false;
                btnNewGame.visible = true;
                btnExtras.visible = true;

                btnNewGame.secondary(null, false);
                btnNewGame.setRect(buttonX, buttonY, buttonWidth, BUTTON_HEIGHT);
                updateExtrasButton();
            }
        } else {
            unlock.visible = true;
            btnLoad.visible = false;
            btnNewGame.visible = false;
            btnExtras.visible = false;
            btnAbandon.visible = false;
        }
    }

    private void updateExtrasButton() {
        if (PixelDungeon.challenges() > 0 || PixelDungeon.godMode()) {
            btnExtras.secondary(TXT_EXTRAS_ON, true);
        } else {
            btnExtras.secondary(null, false);
        }
        btnExtras.setRect(btnNewGame.right() + GAP, buttonY, buttonWidth, BUTTON_HEIGHT);
    }

    private void startNewGame() {

        Dungeon.hero = null;
        InterlevelScene.mode = InterlevelScene.Mode.DESCEND;

        if (PixelDungeon.intro()) {
            PixelDungeon.intro( false );
            Game.switchScene( IntroScene.class );
        } else {
            Game.switchScene( InterlevelScene.class );
        }
    }

    @Override
    protected void onBackPressed() {
        PixelDungeon.switchNoFade( TitleScene.class );
    }

    private static class GameButton extends RedButton {

        private static final int SECONDARY_COLOR_N  = 0xCACFC2;
        private static final int SECONDARY_COLOR_H  = 0xFFFF88;

        private BitmapText secondary;

        public GameButton( String primary ) {
            super( primary );

            this.secondary.text( null );
        }

        @Override
        protected void createChildren() {
            super.createChildren();

            secondary = createText( 6 );
            add( secondary );
        }

        @Override
        protected void layout() {
            super.layout();

            if (secondary.text().length() > 0) {
                text.y = align( y + (height - text.height() - secondary.baseLine()) / 2 );

                secondary.x = align( x + (width - secondary.width()) / 2 );
                secondary.y = align( text.y + text.height() );
            } else {
                text.y = align( y + (height - text.baseLine()) / 2 );
            }
        }

        public void secondary( String text, boolean highlighted ) {
            secondary.text( text );
            secondary.measure();

            secondary.hardlight( highlighted ? SECONDARY_COLOR_H : SECONDARY_COLOR_N );
        }
    }

    private class ClassShield extends Button {

        private static final float MIN_BRIGHTNESS   = 0.6f;

        private static final int BASIC_NORMAL       = 0x444444;
        private static final int BASIC_HIGHLIGHTED  = 0xCACFC2;

        private static final int MASTERY_NORMAL     = 0x666644;
        private static final int MASTERY_HIGHLIGHTED= 0xFFFF88;

        private static final int WIDTH  = 24;
        private static final int HEIGHT = 28;
        private static final int SCALE  = 2;

        private HeroClass cl;

        private Image avatar;
        private BitmapText name;
        private Emitter emitter;

        private float brightness;

        private int normal;
        private int highlighted;

        public ClassShield( HeroClass cl ) {
            super();

            this.cl = cl;

            avatar.frame( cl.ordinal() * WIDTH, 0, WIDTH, HEIGHT );
            avatar.scale.set( SCALE );

            if (Badges.isUnlocked( cl.masteryBadge() )) {
                normal = MASTERY_NORMAL;
                highlighted = MASTERY_HIGHLIGHTED;
            } else {
                normal = BASIC_NORMAL;
                highlighted = BASIC_HIGHLIGHTED;
            }

            name.text( cl.name() );
            name.measure();
            name.hardlight( normal );

            brightness = MIN_BRIGHTNESS;
            updateBrightness();
        }

        @Override
        protected void createChildren() {

            super.createChildren();

            avatar = new Image( Assets.AVATARS );
            add( avatar );

            name = PixelScene.createText( 9 );
            add( name );

            emitter = new BitmaskEmitter( avatar );
            add( emitter );
        }

        @Override
        protected void layout() {

            super.layout();

            avatar.x = align( x + (width - avatar.width()) / 2 );
            avatar.y = align( y + (height - avatar.height() - name.height()) / 2 );

            name.x = align( x + (width - name.width()) / 2 );
            name.y = avatar.y + avatar.height() + SCALE;
        }

        @Override
        protected void onTouchDown() {

            emitter.revive();
            emitter.start( Speck.factory( Speck.LIGHT ), 0.05f, 7 );

            Sample.INSTANCE.play( Assets.SND_CLICK, 1, 1, 1.2f );
            updateClass( cl );
        }

        @Override
        public void update() {
            super.update();

            if (brightness < 1.0f && brightness > MIN_BRIGHTNESS) {
                if ((brightness -= Game.elapsed) <= MIN_BRIGHTNESS) {
                    brightness = MIN_BRIGHTNESS;
                }
                updateBrightness();
            }
        }

        public void highlight( boolean value ) {
            if (value) {
                brightness = 1.0f;
                name.hardlight( highlighted );
            } else {
                brightness = 0.999f;
                name.hardlight( normal );
            }

            updateBrightness();
        }

        private void updateBrightness() {
            avatar.gm = avatar.bm = avatar.rm = avatar.am = brightness;
        }
    }

    private class WndExtras extends Window {

        private static final String TXT_CHALLENGES  = "Challenges";
        private static final String TXT_GOD_MODE    = "God Mode";
        private static final String TXT_GOD_MODE_ON = "Badges, rankings and challenges are disabled in God Mode.";

        private static final int WIDTH      = 112;
        private static final int BTN_HEIGHT = 20;
        private static final int GAP        = 2;

        public WndExtras() {
            super();

            RedButton btnChallenges = new RedButton(TXT_CHALLENGES) {
                @Override
                protected void onClick() {
                    if (PixelDungeon.godMode()) {
                        StartScene.this.add(new WndMessage(TXT_GOD_MODE_ON));
                    } else {
                        hide();
                        StartScene.this.add(new WndChallenges(PixelDungeon.challenges(), true) {
                            @Override
                            public void onBackPressed() {
                                super.onBackPressed();
                                updateExtrasButton();
                            };
                        });
                    }
                }
            };
            btnChallenges.setRect(0, 0, WIDTH, BTN_HEIGHT);
            add(btnChallenges);

            CheckBox btnGodMode = new CheckBox(TXT_GOD_MODE) {
                @Override
                protected void onClick() {
                    super.onClick();
                    PixelDungeon.godMode(checked());

                    if (PixelDungeon.godMode()) {
                        StartScene.this.add(new WndMessage(TXT_GOD_MODE_ON));
                    }
                }
            };
            btnGodMode.setRect(0, btnChallenges.bottom() + GAP, WIDTH, BTN_HEIGHT);
            btnGodMode.checked(PixelDungeon.godMode());
            add(btnGodMode);

            resize(WIDTH, (int) btnGodMode.bottom());
        }

        @Override
        public void onBackPressed() {
            super.onBackPressed();
            updateExtrasButton();
        }

    }

}
