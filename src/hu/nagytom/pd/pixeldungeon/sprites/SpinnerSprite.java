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
package hu.nagytom.pd.pixeldungeon.sprites;

import hu.nagytom.pd.noosa.TextureFilm;
import hu.nagytom.pd.pixeldungeon.Assets;

public class SpinnerSprite extends MobSprite {

    public SpinnerSprite() {
        super();

        texture( Assets.SPINNER );

        TextureFilm frames = new TextureFilm( texture, 16, 16 );

        idle = new Animation( 10, true );
        idle.frames( frames, 0, 0, 0, 0, 0, 1, 0, 1 );

        run = new Animation( 15, true );
        run.frames( frames, 0, 2, 0, 3 );

        attack = new Animation( 12, false );
        attack.frames( frames, 0, 4, 5, 0 );

        die = new Animation( 12, false );
        die.frames( frames, 6, 7, 8, 9 );

        play( idle );
    }

    @Override
    public int blood() {
        return 0xFFBFE5B8;
    }
}
