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
                    GameScores(11, 9, 8, 11, 11, 9, null, null, null, null, null, null, null, null),
                    "Tough game"
                ),
                Match(
                    "2",
                    1,
                    3,
                    0,
                    "Player B",
                    GameScores(11, 7, 11, 5, 11, 6, null, null, null, null, null, null, null, null),
                    "Great performance"
                ),
                Match(
                    "3",
                    5,
                    2,
                    3,
                    "Player C",
                    GameScores(9, 11, 11, 13, 7, 11, 11, 13, null, null, null, null, null, null),
                    "Close match"
                ),
                Match(
                    "4",
                    4,
                    3,
                    1,
                    "Player D",
                    GameScores(11, 5, 11, 7, 8, 11, 11, 7, null, null, null, null, null, null),
                    "Strong finish"
                ),
                Match(
                    "5",
                    2,
                    3,
                    2,
                    "Player E",
                    GameScores(10, 12, 11, 9, 9, 11, 11, 7, null, null, null, null, null, null),
                    "Very close"
                ),
                Match(
                    "6",
                    7,
                    3,
                    1,
                    "Player F",
                    GameScores(11, 6, 11, 9, 9, 11, null, null, null, null, null, null, null, null),
                    "Tough finish"
                ),
                Match(
                    "7",
                    6,
                    1,
                    3,
                    "Player G",
                    GameScores(8, 11, 5, 11, 11, 8, null, null, null, null, null, null, null, null),
                    "Hard loss"
                ),
                Match(
                    "8",
                    10,
                    2,
                    3,
                    "Player H",
                    GameScores(9, 11, 11, 8, 11, 7, 11, 5, null, null, null, null, null, null),
                    "Final match"
                ),
                Match(
                    "9",
                    9,
                    3,
                    0,
                    "Player I",
                    GameScores(11, 6, 11, 7, 11, 8, null, null, null, null, null, null, null, null),
                    "Comfortable win"
                ),
                Match(
                    "10",
                    8,
                    3,
                    2,
                    "Player J",
                    GameScores(11, 7, 9, 11, 11, 8, null, null, null, null, null, null, null, null),
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
                    GameScores(11, 9, 8, 11, 11, 9, null, null, null, null, null, null, null, null),
                    "Good teamwork"
                ),
                Match(
                    "12",
                    1,
                    3,
                    0,
                    "Team B",
                    GameScores(11, 7, 11, 5, 11, 6, null, null, null, null, null, null, null, null),
                    "Great performance"
                ),
                Match(
                    "13",
                    5,
                    2,
                    3,
                    "Team C",
                    GameScores(9, 11, 11, 13, 7, 11, 11, 13, null, null, null, null, null, null),
                    "Close match"
                ),
                Match(
                    "14",
                    4,
                    3,
                    1,
                    "Team D",
                    GameScores(11, 5, 11, 7, 8, 11, 11, 7, null, null, null, null, null, null),
                    "Strong finish"
                ),
                Match(
                    "15",
                    2,
                    3,
                    2,
                    "Team E",
                    GameScores(10, 12, 11, 9, 9, 11, 11, 7, null, null, null, null, null, null),
                    "Very close"
                ),
                Match(
                    "16",
                    7,
                    3,
                    1,
                    "Team F",
                    GameScores(11, 6, 11, 9, 9, 11, null, null, null, null, null, null, null, null),
                    "Tough finish"
                ),
                Match(
                    "17",
                    6,
                    1,
                    3,
                    "Team G",
                    GameScores(8, 11, 5, 11, 11, 8, null, null, null, null, null, null, null, null),
                    "Hard loss"
                ),
                Match(
                    "18",
                    10,
                    2,
                    3,
                    "Team H",
                    GameScores(9, 11, 11, 8, 11, 7, 11, 5, null, null, null, null, null, null),
                    "Final match"
                ),
                Match(
                    "19",
                    9,
                    3,
                    0,
                    "Team I",
                    GameScores(11, 6, 11, 7, 11, 8, null, null, null, null, null, null, null, null),
                    "Comfortable win"
                ),
                Match(
                    "20",
                    8,
                    3,
                    2,
                    "Team J",
                    GameScores(11, 7, 9, 11, 11, 8, null, null, null, null, null, null, null, null),
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
                    GameScores(11, 5, 8, 11, 9, 11, null, null, null, null, null, null, null, null),
                    "Tough match"
                ),
                Match(
                    "22",
                    1,
                    3,
                    0,
                    "Team Beta",
                    GameScores(11, 7, 11, 5, 11, 6, null, null, null, null, null, null, null, null),
                    "Clean sweep"
                ),
                Match(
                    "23",
                    4,
                    2,
                    3,
                    "Team Gamma",
                    GameScores(9, 11, 11, 13, 7, 11, 11, 13, null, null, null, null, null, null),
                    "Close match"
                ),
                Match(
                    "24",
                    3,
                    3,
                    1,
                    "Team Delta",
                    GameScores(11, 5, 11, 7, 8, 11, 11, 7, null, null, null, null, null, null),
                    "Solid win"
                ),
                Match(
                    "25",
                    5,
                    3,
                    2,
                    "Team Epsilon",
                    GameScores(10, 12, 11, 9, 9, 11, 11, 7, null, null, null, null, null, null),
                    "Exciting finish"
                ),
                Match(
                    "26",
                    7,
                    3,
                    1,
                    "Team Zeta",
                    GameScores(11, 6, 11, 9, 9, 11, null, null, null, null, null, null, null, null),
                    "Strong performance"
                ),
                Match(
                    "27",
                    6,
                    1,
                    3,
                    "Team Eta",
                    GameScores(8, 11, 5, 11, 11, 8, null, null, null, null, null, null, null, null),
                    "Hard loss"
                ),
                Match(
                    "28",
                    10,
                    2,
                    3,
                    "Team Theta",
                    GameScores(9, 11, 11, 8, 11, 7, 11, 5, null, null, null, null, null, null),
                    "Nail-biter"
                ),
                Match(
                    "29",
                    9,
                    3,
                    0,
                    "Team Iota",
                    GameScores(11, 6, 11, 7, 11, 8, null, null, null, null, null, null, null, null),
                    "Smooth victory"
                ),
                Match(
                    "30",
                    8,
                    3,
                    2,
                    "Team Kappa",
                    GameScores(11, 7, 9, 11, 11, 8, null, null, null, null, null, null, null, null),
                    "Tough win"
                )
            )
        ),
        Tournament(
            id = "4",
            date = "2024-01-15",
            tournamentName = "Year-End Championships",
            matchType = MatchType.SINGLES,
            matches = listOf(
                Match(
                    "31",
                    2,
                    3,
                    2,
                    "Player Alpha",
                    GameScores(11, 9, 8, 11, 11, 9, null, null, null, null, null, null, null, null),
                    "Great play"
                ),
                Match(
                    "32",
                    1,
                    3,
                    0,
                    "Player Beta",
                    GameScores(11, 7, 11, 5, 11, 6, null, null, null, null, null, null, null, null),
                    "Strong performance"
                ),
                Match(
                    "33",
                    5,
                    2,
                    3,
                    "Player Gamma",
                    GameScores(9, 11, 11, 13, 7, 11, 11, 13, null, null, null, null, null, null),
                    "Hard match"
                ),
                Match(
                    "34",
                    3,
                    3,
                    1,
                    "Player Delta",
                    GameScores(11, 5, 11, 7, 8, 11, 11, 7, null, null, null, null, null, null),
                    "Solid win"
                ),
                Match(
                    "35",
                    6,
                    3,
                    2,
                    "Player Epsilon",
                    GameScores(10, 12, 11, 9, 9, 11, 11, 7, null, null, null, null, null, null),
                    "Close game"
                ),
                Match(
                    "36",
                    8,
                    3,
                    1,
                    "Player Zeta",
                    GameScores(11, 6, 11, 9, 9, 11, null, null, null, null, null, null, null, null),
                    "Strong performance"
                ),
                Match(
                    "37",
                    7,
                    1,
                    3,
                    "Player Eta",
                    GameScores(8, 11, 5, 11, 11, 8, null, null, null, null, null, null, null, null),
                    "Hard loss"
                ),
                Match(
                    "38",
                    10,
                    2,
                    3,
                    "Player Theta",
                    GameScores(9, 11, 11, 8, 11, 7, 11, 5, null, null, null, null, null, null),
                    "Final showdown"
                ),
                Match(
                    "39",
                    9,
                    3,
                    0,
                    "Player Iota",
                    GameScores(11, 6, 11, 7, 11, 8, null, null, null, null, null, null, null, null),
                    "Smooth victory"
                ),
                Match(
                    "40",
                    4,
                    3,
                    2,
                    "Player Kappa",
                    GameScores(11, 7, 9, 11, 11, 8, null, null, null, null, null, null, null, null),
                    "Tough win"
                )
            )
        ),
        Tournament(
            id = "5",
            date = "2023-12-20",
            tournamentName = "New Year Open 2024",
            matchType = MatchType.TEAM,
            matches = listOf(
                Match(
                    "41",
                    3,
                    3,
                    2,
                    "Team A",
                    GameScores(11, 9, 8, 11, 11, 9, null, null, null, null, null, null, null, null),
                    "Close game"
                ),
                Match(
                    "42",
                    1,
                    3,
                    0,
                    "Team B",
                    GameScores(11, 7, 11, 5, 11, 6, null, null, null, null, null, null, null, null),
                    "Dominant victory"
                ),
                Match(
                    "43",
                    5,
                    2,
                    3,
                    "Team C",
                    GameScores(9, 11, 11, 13, 7, 11, 11, 13, null, null, null, null, null, null),
                    "Close match"
                ),
                Match(
                    "44",
                    4,
                    3,
                    1,
                    "Team D",
                    GameScores(11, 5, 11, 7, 8, 11, 11, 7, null, null, null, null, null, null),
                    "Strong finish"
                ),
                Match(
                    "45",
                    6,
                    3,
                    2,
                    "Team E",
                    GameScores(10, 12, 11, 9, 9, 11, 11, 7, null, null, null, null, null, null),
                    "Nail-biter"
                ),
                Match(
                    "46",
                    8,
                    3,
                    1,
                    "Team F",
                    GameScores(11, 6, 11, 9, 9, 11, null, null, null, null, null, null, null, null),
                    "Solid win"
                ),
                Match(
                    "47",
                    7,
                    1,
                    3,
                    "Team G",
                    GameScores(8, 11, 5, 11, 11, 8, null, null, null, null, null, null, null, null),
                    "Hard loss"
                ),
                Match(
                    "48",
                    10,
                    2,
                    3,
                    "Team H",
                    GameScores(9, 11, 11, 8, 11, 7, 11, 5, null, null, null, null, null, null),
                    "Final match"
                ),
                Match(
                    "49",
                    9,
                    3,
                    0,
                    "Team I",
                    GameScores(11, 6, 11, 7, 11, 8, null, null, null, null, null, null, null, null),
                    "Smooth win"
                ),
                Match(
                    "50",
                    2,
                    3,
                    2,
                    "Team J",
                    GameScores(11, 7, 9, 11, 11, 8, null, null, null, null, null, null, null, null),
                    "Close call"
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