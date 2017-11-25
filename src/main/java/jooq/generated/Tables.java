/**
 * This class is generated by jOOQ
 */
package jooq.generated;


import javax.annotation.Generated;

import jooq.generated.tables.Player;
import jooq.generated.tables.PlayerHittingStatline;
import jooq.generated.tables.PlayerPitchingStatline;
import jooq.generated.tables.Position;
import jooq.generated.tables.Team;
import jooq.generated.tables.Tweet;
import jooq.generated.tables.User;


/**
 * Convenience access to all tables in public
 */
@Generated(
	value = {
		"http://www.jooq.org",
		"jOOQ version:3.7.1"
	},
	comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Tables {

	/**
	 * The table public.player
	 */
	public static final Player PLAYER = jooq.generated.tables.Player.PLAYER;

	/**
	 * The table public.player_hitting_statline
	 */
	public static final PlayerHittingStatline PLAYER_HITTING_STATLINE = jooq.generated.tables.PlayerHittingStatline.PLAYER_HITTING_STATLINE;

	/**
	 * The table public.player_pitching_statline
	 */
	public static final PlayerPitchingStatline PLAYER_PITCHING_STATLINE = jooq.generated.tables.PlayerPitchingStatline.PLAYER_PITCHING_STATLINE;

	/**
	 * The table public.position
	 */
	public static final Position POSITION = jooq.generated.tables.Position.POSITION;

	/**
	 * The table public.team
	 */
	public static final Team TEAM = jooq.generated.tables.Team.TEAM;

	/**
	 * The table public.tweet
	 */
	public static final Tweet TWEET = jooq.generated.tables.Tweet.TWEET;

	/**
	 * The table public.user
	 */
	public static final User USER = jooq.generated.tables.User.USER;
}
