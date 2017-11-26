create table player
(
	player_id serial not null
		constraint player_player_id_pk
			primary key,
	player_name varchar,
	player_name_plain varchar,
	team_id integer,
	mlb_player_id varchar
)
;

create table tweet
(
	tweet_id serial,
	message varchar,
	created_timestamp timestamp with time zone,
	player_id integer
		constraint tweet_player_player_id_fk
			references player,
	user_id integer not null
)
;

create table team
(
	team_id serial not null
		constraint team_pkey
			primary key,
	team_name varchar,
	team_code_mlb varchar
)
;

create unique index team_team_id_uindex
	on team (team_id)
;

alter table player
	add constraint player_team_team_id_fk
		foreign key (team_id) references team
;

create table position
(
	position_d serial not null
		constraint position_pkey
			primary key,
	position_name varchar
)
;

create unique index position_position_d_uindex
	on position (position_d)
;

create table "user"
(
	user_id serial not null
		constraint user_pkey
			primary key,
	username varchar,
	email_address varchar,
	first_name varchar,
	last_name varchar,
	account_locked boolean,
	password varchar
)
;

create unique index user_username_uindex
	on "user" (username)
;

alter table tweet
	add constraint tweet_user_user_id_fk
		foreign key (user_id) references "user"
;

create table player_hitting_statline
(
	hitting_statline_id serial not null
		constraint player_hitting_statline_pkey
			primary key,
	year integer not null,
	team_id integer,
	games integer,
	atbats integer,
	runs integer,
	hits integer,
	total_bases integer,
	doubles integer,
	triples integer,
	home_runs integer,
	rbis integer,
	walks integer,
	intentional_walks integer,
	strike_outs integer,
	stolen_bases integer,
	caught_stealing integer,
	average double precision,
	obp double precision,
	slg double precision,
	ops double precision,
	player_id integer
		constraint player_hitting_statline_player_player_id_fk
			references player
)
;

create unique index player_hitting_statline_hitting_statline_id_uindex
	on player_hitting_statline (hitting_statline_id)
;

create table player_pitching_statline
(
	pitching_statline_id serial not null
		constraint player_pitching_statline_pkey
			primary key,
	year integer not null,
	team_id integer,
	wins integer,
	losses integer,
	era double precision,
	games integer,
	games_started integer,
	complete_games integer,
	shutouts integer,
	complete_game_shutouts integer,
	saves integer,
	save_opportunities integer,
	innings integer,
	hits integer,
	runs integer,
	earned_runs integer,
	home_runs integer,
	hit_batsmen integer,
	walks integer,
	intentional_walks integer,
	strike_outs integer,
	batting_average_against double precision,
	whip double precision,
	player_id integer
)
;

create unique index player_pitching_statline_pitching_statline_id_uindex
	on player_pitching_statline (pitching_statline_id)
;

