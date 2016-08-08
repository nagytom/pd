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
package hu.nagytom.pd.pixeldungeon;

import javax.microedition.khronos.opengles.GL10;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;

import hu.nagytom.pd.noosa.Game;
import hu.nagytom.pd.noosa.audio.Music;
import hu.nagytom.pd.noosa.audio.Sample;
import hu.nagytom.pd.pixeldungeon.scenes.GameScene;
import hu.nagytom.pd.pixeldungeon.scenes.PixelScene;
import hu.nagytom.pd.pixeldungeon.scenes.TitleScene;

public class PixelDungeon extends Game {

    public PixelDungeon() {
        super( TitleScene.class );

        hu.nagytom.pd.utils.Bundle.addAlias(
            hu.nagytom.pd.pixeldungeon.items.scrolls.ScrollOfUpgrade.class,
            "hu.nagytom.pd.pixeldungeon.items.scrolls.ScrollOfEnhancement" );
        hu.nagytom.pd.utils.Bundle.addAlias(
            hu.nagytom.pd.pixeldungeon.actors.blobs.WaterOfHealth.class,
            "hu.nagytom.pd.pixeldungeon.actors.blobs.Light" );
        hu.nagytom.pd.utils.Bundle.addAlias(
            hu.nagytom.pd.pixeldungeon.items.rings.RingOfMending.class,
            "hu.nagytom.pd.pixeldungeon.items.rings.RingOfRejuvenation" );
        hu.nagytom.pd.utils.Bundle.addAlias(
            hu.nagytom.pd.pixeldungeon.items.wands.WandOfReach.class,
            "hu.nagytom.pd.pixeldungeon.items.wands.WandOfTelekenesis" );
        hu.nagytom.pd.utils.Bundle.addAlias(
            hu.nagytom.pd.pixeldungeon.actors.blobs.Foliage.class,
            "hu.nagytom.pd.pixeldungeon.actors.blobs.Blooming" );
        hu.nagytom.pd.utils.Bundle.addAlias(
            hu.nagytom.pd.pixeldungeon.actors.buffs.Shadows.class,
            "hu.nagytom.pd.pixeldungeon.actors.buffs.Rejuvenation" );
        hu.nagytom.pd.utils.Bundle.addAlias(
            hu.nagytom.pd.pixeldungeon.items.scrolls.ScrollOfPsionicBlast.class,
            "hu.nagytom.pd.pixeldungeon.items.scrolls.ScrollOfNuclearBlast" );
        hu.nagytom.pd.utils.Bundle.addAlias(
            hu.nagytom.pd.pixeldungeon.actors.hero.Hero.class,
            "hu.nagytom.pd.pixeldungeon.actors.Hero" );
        hu.nagytom.pd.utils.Bundle.addAlias(
            hu.nagytom.pd.pixeldungeon.actors.mobs.npcs.Shopkeeper.class,
            "hu.nagytom.pd.pixeldungeon.actors.mobs.Shopkeeper" );
        // 1.6.1
        hu.nagytom.pd.utils.Bundle.addAlias(
            hu.nagytom.pd.pixeldungeon.items.quest.DriedRose.class,
            "hu.nagytom.pd.pixeldungeon.items.DriedRose" );
        hu.nagytom.pd.utils.Bundle.addAlias(
            hu.nagytom.pd.pixeldungeon.actors.mobs.npcs.MirrorImage.class,
            "hu.nagytom.pd.pixeldungeon.items.scrolls.ScrollOfMirrorImage$MirrorImage" );
        // 1.6.4
        hu.nagytom.pd.utils.Bundle.addAlias(
            hu.nagytom.pd.pixeldungeon.items.rings.RingOfElements.class,
            "hu.nagytom.pd.pixeldungeon.items.rings.RingOfCleansing" );
        hu.nagytom.pd.utils.Bundle.addAlias(
            hu.nagytom.pd.pixeldungeon.items.rings.RingOfElements.class,
            "hu.nagytom.pd.pixeldungeon.items.rings.RingOfResistance" );
        hu.nagytom.pd.utils.Bundle.addAlias(
            hu.nagytom.pd.pixeldungeon.items.weapon.missiles.Boomerang.class,
            "hu.nagytom.pd.pixeldungeon.items.weapon.missiles.RangersBoomerang" );
        hu.nagytom.pd.utils.Bundle.addAlias(
            hu.nagytom.pd.pixeldungeon.items.rings.RingOfPower.class,
            "hu.nagytom.pd.pixeldungeon.items.rings.RingOfEnergy" );
        // 1.7.2
        hu.nagytom.pd.utils.Bundle.addAlias(
            hu.nagytom.pd.pixeldungeon.plants.Dreamweed.class,
            "hu.nagytom.pd.pixeldungeon.plants.Blindweed" );
        hu.nagytom.pd.utils.Bundle.addAlias(
            hu.nagytom.pd.pixeldungeon.plants.Dreamweed.Seed.class,
            "hu.nagytom.pd.pixeldungeon.plants.Blindweed$Seed" );
        // 1.7.4
        hu.nagytom.pd.utils.Bundle.addAlias(
            hu.nagytom.pd.pixeldungeon.items.weapon.enchantments.Shock.class,
            "hu.nagytom.pd.pixeldungeon.items.weapon.enchantments.Piercing" );
        hu.nagytom.pd.utils.Bundle.addAlias(
            hu.nagytom.pd.pixeldungeon.items.weapon.enchantments.Shock.class,
            "hu.nagytom.pd.pixeldungeon.items.weapon.enchantments.Swing" );
        hu.nagytom.pd.utils.Bundle.addAlias(
            hu.nagytom.pd.pixeldungeon.items.scrolls.ScrollOfEnchantment.class,
            "hu.nagytom.pd.pixeldungeon.items.scrolls.ScrollOfWeaponUpgrade" );
        // 1.7.5
        hu.nagytom.pd.utils.Bundle.addAlias(
            hu.nagytom.pd.pixeldungeon.items.scrolls.ScrollOfEnchantment.class,
            "hu.nagytom.pd.pixeldungeon.items.Stylus" );
        // 1.8.0
        hu.nagytom.pd.utils.Bundle.addAlias(
            hu.nagytom.pd.pixeldungeon.actors.mobs.FetidRat.class,
            "hu.nagytom.pd.pixeldungeon.actors.mobs.npcs.Ghost$FetidRat" );
        hu.nagytom.pd.utils.Bundle.addAlias(
            hu.nagytom.pd.pixeldungeon.plants.Rotberry.class,
            "hu.nagytom.pd.pixeldungeon.actors.mobs.npcs.Wandmaker$Rotberry" );
        hu.nagytom.pd.utils.Bundle.addAlias(
            hu.nagytom.pd.pixeldungeon.plants.Rotberry.Seed.class,
            "hu.nagytom.pd.pixeldungeon.actors.mobs.npcs.Wandmaker$Rotberry$Seed" );
        // 1.9.0
        hu.nagytom.pd.utils.Bundle.addAlias(
            hu.nagytom.pd.pixeldungeon.items.wands.WandOfReach.class,
            "hu.nagytom.pd.pixeldungeon.items.wands.WandOfTelekinesis" );
    }

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );

        updateImmersiveMode();

        DisplayMetrics metrics = new DisplayMetrics();
        instance.getWindowManager().getDefaultDisplay().getMetrics( metrics );
        boolean landscape = metrics.widthPixels > metrics.heightPixels;

        if (Preferences.INSTANCE.getBoolean( Preferences.KEY_LANDSCAPE, false ) != landscape) {
            landscape( !landscape );
        }

        Music.INSTANCE.enable( music() );
        Sample.INSTANCE.enable( soundFx() );

        Sample.INSTANCE.load(
            Assets.SND_CLICK,
            Assets.SND_BADGE,
            Assets.SND_GOLD,

            Assets.SND_DESCEND,
            Assets.SND_STEP,
            Assets.SND_WATER,
            Assets.SND_OPEN,
            Assets.SND_UNLOCK,
            Assets.SND_ITEM,
            Assets.SND_DEWDROP,
            Assets.SND_HIT,
            Assets.SND_MISS,
            Assets.SND_EAT,
            Assets.SND_READ,
            Assets.SND_LULLABY,
            Assets.SND_DRINK,
            Assets.SND_SHATTER,
            Assets.SND_ZAP,
            Assets.SND_LIGHTNING,
            Assets.SND_LEVELUP,
            Assets.SND_DEATH,
            Assets.SND_CHALLENGE,
            Assets.SND_CURSED,
            Assets.SND_EVOKE,
            Assets.SND_TRAP,
            Assets.SND_TOMB,
            Assets.SND_ALERT,
            Assets.SND_MELD,
            Assets.SND_BOSS,
            Assets.SND_BLAST,
            Assets.SND_PLANT,
            Assets.SND_RAY,
            Assets.SND_BEACON,
            Assets.SND_TELEPORT,
            Assets.SND_CHARMS,
            Assets.SND_MASTERY,
            Assets.SND_PUFF,
            Assets.SND_ROCKS,
            Assets.SND_BURNING,
            Assets.SND_FALLING,
            Assets.SND_GHOST,
            Assets.SND_SECRET,
            Assets.SND_BONES,
            Assets.SND_BEE,
            Assets.SND_DEGRADE,
            Assets.SND_MIMIC );
    }

    @Override
    public void onWindowFocusChanged( boolean hasFocus ) {

        super.onWindowFocusChanged( hasFocus );

        if (hasFocus) {
            updateImmersiveMode();
        }
    }

    public static void switchNoFade( Class<? extends PixelScene> c ) {
        PixelScene.noFade = true;
        switchScene( c );
    }

    /*
     * ---> Prefernces
     */

    public static void landscape( boolean value ) {
        Game.instance.setRequestedOrientation( value ?
            ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE :
            ActivityInfo.SCREEN_ORIENTATION_PORTRAIT );
        Preferences.INSTANCE.put( Preferences.KEY_LANDSCAPE, value );
    }

    public static boolean landscape() {
        return width > height;
    }

    // *** IMMERSIVE MODE ****

    private static boolean immersiveModeChanged = false;

    @SuppressLint("NewApi")
    public static void immerse( boolean value ) {
        Preferences.INSTANCE.put( Preferences.KEY_IMMERSIVE, value );

        instance.runOnUiThread( new Runnable() {
            @Override
            public void run() {
                updateImmersiveMode();
                immersiveModeChanged = true;
            }
        } );
    }

    @Override
    public void onSurfaceChanged( GL10 gl, int width, int height ) {
        super.onSurfaceChanged( gl, width, height );

        if (immersiveModeChanged) {
            requestedReset = true;
            immersiveModeChanged = false;
        }
    }

    @SuppressLint("NewApi")
    public static void updateImmersiveMode() {
        if (android.os.Build.VERSION.SDK_INT >= 19) {
            try {
                // Sometime NullPointerException happens here
                instance.getWindow().getDecorView().setSystemUiVisibility(
                    immersed() ?
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                    View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                    View.SYSTEM_UI_FLAG_FULLSCREEN |
                    View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    :
                    0 );
            } catch (Exception e) {
                reportException( e );
            }
        }
    }

    public static boolean immersed() {
        return Preferences.INSTANCE.getBoolean( Preferences.KEY_IMMERSIVE, false );
    }

    // *****************************

    public static void scaleUp( boolean value ) {
        Preferences.INSTANCE.put( Preferences.KEY_SCALE_UP, value );
        switchScene( TitleScene.class );
    }

    public static boolean scaleUp() {
        return Preferences.INSTANCE.getBoolean( Preferences.KEY_SCALE_UP, true );
    }

    public static void zoom( int value ) {
        Preferences.INSTANCE.put( Preferences.KEY_ZOOM, value );
    }

    public static int zoom() {
        return Preferences.INSTANCE.getInt( Preferences.KEY_ZOOM, 0 );
    }

    public static void music( boolean value ) {
        Music.INSTANCE.enable( value );
        Preferences.INSTANCE.put( Preferences.KEY_MUSIC, value );
    }

    public static boolean music() {
        return Preferences.INSTANCE.getBoolean( Preferences.KEY_MUSIC, true );
    }

    public static void soundFx( boolean value ) {
        Sample.INSTANCE.enable( value );
        Preferences.INSTANCE.put( Preferences.KEY_SOUND_FX, value );
    }

    public static boolean soundFx() {
        return Preferences.INSTANCE.getBoolean( Preferences.KEY_SOUND_FX, true );
    }

    public static void brightness( boolean value ) {
        Preferences.INSTANCE.put( Preferences.KEY_BRIGHTNESS, value );
        if (scene() instanceof GameScene) {
            ((GameScene)scene()).brightness( value );
        }
    }

    public static boolean brightness() {
        return Preferences.INSTANCE.getBoolean( Preferences.KEY_BRIGHTNESS, false );
    }

    public static void donated( String value ) {
        Preferences.INSTANCE.put( Preferences.KEY_DONATED, value );
    }

    public static String donated() {
        return Preferences.INSTANCE.getString( Preferences.KEY_DONATED, "" );
    }

    public static void lastClass( int value ) {
        Preferences.INSTANCE.put( Preferences.KEY_LAST_CLASS, value );
    }

    public static int lastClass() {
        return Preferences.INSTANCE.getInt( Preferences.KEY_LAST_CLASS, 0 );
    }

    public static void challenges( int value ) {
        Preferences.INSTANCE.put( Preferences.KEY_CHALLENGES, value );
    }

    public static int challenges() {
        return Preferences.INSTANCE.getInt( Preferences.KEY_CHALLENGES, 0 );
    }

    public static void intro( boolean value ) {
        Preferences.INSTANCE.put( Preferences.KEY_INTRO, value );
    }

    public static boolean intro() {
        return Preferences.INSTANCE.getBoolean( Preferences.KEY_INTRO, true );
    }

    /*
     * <--- Preferences
     */

    public static void reportException( Throwable tr ) {
        Log.e( "PD", Log.getStackTraceString( tr ) );
    }
}
