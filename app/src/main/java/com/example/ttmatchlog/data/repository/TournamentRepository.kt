package com.example.ttmatchlog.data.repository

import com.example.ttmatchlog.data.model.GameScores
import com.example.ttmatchlog.data.model.Match
import com.example.ttmatchlog.data.model.MatchType
import com.example.ttmatchlog.data.model.Tournament

// TournamentRepository.kt
class TournamentRepository {

    // List
    val tournaments_example = listOf(
        Tournament(
            id = "1",
            date = "2024-10-05",
            tournamentName = "Summer Open 2023",
            matchType = MatchType.SINGLES,
            matches = listOf(
                Match(
                    "1",
                    3,
                    3,
                    2,
                    "Player A",
                    GameScores(11, 9, 8, 11, 11, 9, 0, 0, 0, 0, 0, 0, 0, 0),
                    "Tough game"
                ),
                Match(
                    "2",
                    1,
                    3,
                    0,
                    "Player B",
                    GameScores(11, 7, 11, 5, 11, 6, 0, 0, 0, 0, 0, 0, 0, 0),
                    "Great performance"
                ),
                Match(
                    "3",
                    5,
                    2,
                    3,
                    "Player C",
                    GameScores(9, 11, 11, 13, 7, 11, 11, 13, 0, 0, 0, 0, 0, 0),
                    "Close match"
                ),
                Match(
                    "4",
                    4,
                    3,
                    1,
                    "Player D",
                    GameScores(11, 5, 11, 7, 8, 11, 11, 7, 0, 0, 0, 0, 0, 0),
                    "Strong finish"
                ),
                Match(
                    "5",
                    2,
                    3,
                    2,
                    "Player E",
                    GameScores(10, 12, 11, 9, 9, 11, 11, 7, 0, 0, 0, 0, 0, 0),
                    "Very close"
                ),
                Match(
                    "6",
                    7,
                    3,
                    1,
                    "Player F",
                    GameScores(11, 6, 11, 9, 9, 11, 0, 0, 0, 0, 0, 0, 0, 0),
                    "Tough finish"
                ),
                Match(
                    "7",
                    6,
                    1,
                    3,
                    "Player G",
                    GameScores(8, 11, 5, 11, 11, 8, 0, 0, 0, 0, 0, 0, 0, 0),
                    "Hard loss"
                ),
                Match(
                    "8",
                    10,
                    2,
                    3,
                    "Player H",
                    GameScores(9, 11, 11, 8, 11, 7, 11, 5, 0, 0, 0, 0, 0, 0),
                    "Final match"
                ),
                Match(
                    "9",
                    9,
                    3,
                    0,
                    "Player I",
                    GameScores(11, 6, 11, 7, 11, 8, 0, 0, 0, 0, 0, 0, 0, 0),
                    "Comfortable win"
                ),
                Match(
                    "10",
                    8,
                    3,
                    2,
                    "Player J",
                    GameScores(11, 7, 9, 11, 11, 8, 0, 0, 0, 0, 0, 0, 0, 0),
                    "Tight win"
                )
            )
        ),
        Tournament(
            id = "2",
            date = "2024-10-05",
            tournamentName = "Autumn Cup 2023",
            matchType = MatchType.DOUBLES,
            matches = listOf(
                Match(
                    "11",
                    3,
                    3,
                    2,
                    "Team A",
                    GameScores(11, 9, 8, 11, 11, 9, 0, 0, 0, 0, 0, 0, 0, 0),
                    "Good teamwork"
                ),
                Match(
                    "12",
                    1,
                    3,
                    0,
                    "Team B",
                    GameScores(11, 7, 11, 5, 11, 6, 0, 0, 0, 0, 0, 0, 0, 0),
                    "Great performance"
                ),
                Match(
                    "13",
                    5,
                    2,
                    3,
                    "Team C",
                    GameScores(9, 11, 11, 13, 7, 11, 11, 13, 0, 0, 0, 0, 0, 0),
                    "Close match"
                ),
                Match(
                    "14",
                    4,
                    3,
                    1,
                    "Team D",
                    GameScores(11, 5, 11, 7, 8, 11, 11, 7, 0, 0, 0, 0, 0, 0),
                    "Strong finish"
                ),
                Match(
                    "15",
                    2,
                    3,
                    2,
                    "Team E",
                    GameScores(10, 12, 11, 9, 9, 11, 11, 7, 0, 0, 0, 0, 0, 0),
                    "Very close"
                ),
                Match(
                    "16",
                    7,
                    3,
                    1,
                    "Team F",
                    GameScores(11, 6, 11, 9, 9, 11, 0, 0, 0, 0, 0, 0, 0, 0),
                    "Tough finish"
                ),
                Match(
                    "17",
                    6,
                    1,
                    3,
                    "Team G",
                    GameScores(8, 11, 5, 11, 11, 8, 0, 0, 0, 0, 0, 0, 0, 0),
                    "Hard loss"
                ),
                Match(
                    "18",
                    10,
                    2,
                    3,
                    "Team H",
                    GameScores(9, 11, 11, 8, 11, 7, 11, 5, 0, 0, 0, 0, 0, 0),
                    "Final match"
                ),
                Match(
                    "19",
                    9,
                    3,
                    0,
                    "Team I",
                    GameScores(11, 6, 11, 7, 11, 8, 0, 0, 0, 0, 0, 0, 0, 0),
                    "Comfortable win"
                ),
                Match(
                    "20",
                    8,
                    3,
                    2,
                    "Team J",
                    GameScores(11, 7, 9, 11, 11, 8, 0, 0, 0, 0, 0, 0, 0, 0),
                    "Tight win"
                )
            )
        ),
        Tournament(
            id = "3",
            date = "2023-11-11",
            tournamentName = "Winter Showdown",
            matchType = MatchType.TEAM,
            matches = listOf(
                Match(
                    "21",
                    2,
                    3,
                    2,
                    "Team Alpha",
                    GameScores(11, 5, 8, 11, 9, 11, 0, 0, 0, 0, 0, 0, 0, 0),
                    "Tough match"
                ),
                Match(
                    "22",
                    1,
                    3,
                    0,
                    "Team Beta",
                    GameScores(11, 7, 11, 5, 11, 6, 0, 0, 0, 0, 0, 0, 0, 0),
                    "Clean sweep"
                ),
                Match(
                    "23",
                    4,
                    2,
                    3,
                    "Team Gamma",
                    GameScores(9, 11, 11, 13, 7, 11, 11, 13, 0, 0, 0, 0, 0, 0),
                    "Close match"
                ),
                Match(
                    "24",
                    3,
                    3,
                    1,
                    "Team Delta",
                    GameScores(11, 5, 11, 7, 8, 11, 11, 7, 0, 0, 0, 0, 0, 0),
                    "Solid win"
                )
            )
        )
    )



        fun fetchTournaments(): List<Tournament> {
            // tournamentsデータをここに移動する
//        return listOf(
//            // 同じtournamentデータ
//        )
            return tournaments_example
        }
}