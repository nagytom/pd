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
package hu.nagytom.pd.pixeldungeon.effects.particles;

import hu.nagytom.pd.noosa.particles.Emitter;
import hu.nagytom.pd.noosa.particles.PixelParticle;
import hu.nagytom.pd.noosa.particles.Emitter.Factory;
import hu.nagytom.pd.utils.ColorMath;
import hu.nagytom.pd.utils.Random;

public class WoolParticle extends PixelParticle.Shrinking {

    public static final Emitter.Factory FACTORY = new Factory() {
        @Override
        public void emit( Emitter emitter, int index, float x, float y ) {
            ((WoolParticle)emitter.recycle( WoolParticle.class )).reset( x, y );
        }
    };

    public WoolParticle() {
        super();

        color( ColorMath.random( 0x999999, 0xEEEEE0 ) );

        acc.set( 0, -40 );
    }

    public void reset( float x, float y ) {
        revive();

        this.x = x;
        this.y = y;

        left = lifespan = Random.Float( 0.6f, 1f );
        size = 5;

        speed.set( Random.Float( -10, +10 ), Random.Float( -10, +10 ) );
    }
}
