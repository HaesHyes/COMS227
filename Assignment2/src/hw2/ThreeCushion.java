package hw2;

import api.BallType;
import api.PlayerPosition;

import java.awt.*;

import static api.BallType.*;
import static api.PlayerPosition.*;

/**
 * Class that models the game of three-cushion billiards.
 *
 * @author Haesung Michael Lee
 */
public class ThreeCushion {
    //Variables
    /**
     * How many points need to be scored to win the game
     */
    private final int PointsToWinGame;
    /**
     * Helps keep track of who will be the player using the LagWinner Method
     */
    private final PlayerPosition LagWinnerStart;
    /**
     * Helps determine selfBreak in LagWInner
     */
    private final PlayerPosition LagSecond;
    /**
     * Keeps track of the current player, switches between Player A and B
     */
    private PlayerPosition CurrentInningPlayer;
    /**
     * Keeps track of the current player's ball, switches between Player A's cueball and B's
     */
    private BallType CurrentBall;
    /**
     * Keeps track of how many valid ball hits were made
     */
    private int BallHit;
    /**
     * Keeps track of the amount of Innings
     */
    private int Inning;
    /**
     * Keeps track on if an inning has started or not
     */
    private Boolean InningStatus = false;
    /**
     * Keeps track on if a strike has started that turn
     */
    private Boolean shotStart = false;
    /**
     * Keeps track of how many sides are being hit
     */
    private int CushionHit;
    /**
     * Player A score
     */
    private int PlayerAScore;
    /**
     * Player B score
     */
    private int PlayerBScore;
    /**
     * Lets the other code run, if false makes it so nothing can run
     */
    private Boolean LagShotConfirmed = false;
    /**
     * How we know if game is over, also if set true does not let any code run
     */
    private Boolean GameOver = false;
    /**
     * Tests to see if it still is the first shot of the game
     */
    private Boolean IsItStillBreakshot = true;
    /**
     * Returns to endShot that the hit was valid and a point needs to be awarded to the current player
     */
    private Boolean WasShotValid = false;
    /**
     * Tells cueBallStrike that the StickStrike worked properly and was valid or not
     */
    private Boolean CueStickHitValid = false;
    /**
     * Inning starts if the stick was called and can foul if called twice, differs from Valid because a cueStick can be called but not be valid
     */
    private Boolean HasCueStickStart = false;
    /**
     * Sets to true or false depending on if the conditionals of a BankShot were met
     */
    private Boolean BankShot = false;
    /**
     * Keeps track of the previous ball hit to make sure no duplicate points r awarded
     */
    private BallType PreviousBall;
    /**
     * Sends a message to the endShot from foul that the inning was already changed in order not have a double inning add
     */
    private Boolean InningUpdatedAlready = false;
    /**
     * Will not let StickStrike call unless reported that balls stop rolling
     */
    private Boolean BallsStillRolling = false;


    //Constructor

    /**
     * Creates a new game of three-cushion billiards with a given lag winner and the predetermined number of points required to win the game.
     *
     * @param lagWinner   person doing the lag
     * @param pointsToWin how many to win
     */
    public ThreeCushion(PlayerPosition lagWinner, int pointsToWin) {
        Inning = 1; // Inning starts at 1
        PointsToWinGame = pointsToWin;
        LagWinnerStart = lagWinner;
        if (LagWinnerStart == PLAYER_A) { // Sets up who will run LagWinner
            LagSecond = PLAYER_B;
        } else {
            LagSecond = PLAYER_A;
        }
    }

    /**
     * Outputs the current game status
     *
     * @return Current game status
     */
    public String toString() {
        String fmt = "Player A%s: %d, Player B%s: %d, Inning: %d %s%s";
        String playerATurn = "";
        String playerBTurn = "";
        String inningStatus = "";
        String gameStatus = "";
        if (getInningPlayer() == PLAYER_A) {
            playerATurn = "*";
        } else if (getInningPlayer() == PLAYER_B) {
            playerBTurn = "*";
        }
        if (isInningStarted()) {
            inningStatus = "started";
        } else {
            inningStatus = "not started";
        }
        if (isGameOver()) {
            gameStatus = ", game result final";
        }
        return String.format(fmt, playerATurn, getPlayerAScore(), playerBTurn, getPlayerBScore(), getInning(), inningStatus, gameStatus);
    }

    /**
     * Adds to the counter if the cushion is struck, also sets BankShot = true if the hit condition is met
     */
    public void cueBallImpactCushion() {
        if (!GameOver) {
            CushionHit += 1;
            if (CushionHit >= 3 && BallHit == 0) { // if conditions met for a Bankshot set it to true
                BankShot = true;
            }
        }
    }

    /**
     * Takes the ball the player has struck and checks if it has not been struck before or
     * is the player's ball, if it passes, add one point
     * If the IsItStillBreakshot boolean is set to true, it will instead run an alternate code
     * that will check for break shot specific conditionals
     *
     * @param ball ball being struck
     */
    public void cueBallStrike(BallType ball) {
        if (!GameOver) {
            if (CueStickHitValid) {
                if (!IsItStillBreakshot) {
                    if (ball != CurrentBall && ball != PreviousBall) {// Hit of a ball either of the player's or one already hit will not award a point
                        if (CushionHit <= 3 && BallHit >= 3) {
                            foul();
                            WasShotValid = false;
                        }
                        BallHit += 1;
                        PreviousBall = ball; // Previous ball is originally CurrentBall, after it runs the first time now there is basically a new conditional added to the if
                    }
                } else {
                    if (BallHit == 0) { // breakshot specific, this if only runs once and then is defunct for the rest of the break
                        if (ball == RED && CushionHit == 0) {
                            BallHit += 1;
                        } else {
                            foul();
                            WasShotValid = false;
                        }
                    } else if (BallHit >= 1 && ball != RED) {
                        if (ball != CurrentBall) {
                            BallHit += 1;
                        }
                    }
                }
                if (CushionHit < 3 && BallHit == 2) {
                    foul();
                    WasShotValid = false;
                } else if (CushionHit >= 3 && BallHit == 2) {
                    WasShotValid = true;//runs for all the BallStrike no matter if its breakshot or not, tests to see if a shot was valid or not
                }
            }
        }
    }

    /**
     * Tests to see if game is over, if balls are rolling, if both are false it returns the cue stick strike was started
     * and resets the bankshot parameter
     * if the shot has started then it will return a foul\
     * the current player must hit their ball, if not their own ball then they will recieve a foul
     *
     * @param ball The Ball the player is hitting, must be theirs
     */
    public void cueStickStrike(BallType ball) {
        if (!GameOver) {
            if (!BallsStillRolling) { // won't run if balls are still rolling
                HasCueStickStart = true; // signals cue has started but not valid
                CueStickHitValid = false;
                BankShot = false; // bankshot gets set false here because it is the first method to run in a new inning, end shot should not change bank
                BallsStillRolling = true; // balls are now rolling
                if (shotStart) {
                    foul(); // cannot run again
                }
                if (CurrentBall == ball) {
                    PreviousBall = ball; // set up for ball strike
                    if (!shotStart) { // will only run if shot has not already been started
                        shotStart = true;
                        InningStatus = true;
                        CueStickHitValid = true;
                    }
                } else {
                    foul();
                    CueStickHitValid = false; // if conditions are met, signal to ball strike that it was a valid stick strike
                }
            } else {
                foul();
            }
        } else InningStatus = false;
    }

    /**
     * Calls for the shot to be over, adds points to the players if warranted,
     * resets variables
     * if shot was not valid then it will switch players and add innings if foul has not done so already
     */
    public void endShot() {
        if (!GameOver) {
            if (LagShotConfirmed) {
                IsItStillBreakshot = false; // Breakshot is only set true once and that method is called only once, this guarantees it will only run be true for one shot
                if (HasCueStickStart) {
                    if (shotStart = true) {

                        if (BallHit <= 1) {
                            BankShot = false; // double-checking if the bank-shot was a valid shot, if not it does nothing
                        }
                        //InningStatus = false;
                        shotStart = false; // resets all the values
                        HasCueStickStart = false;
                        BallsStillRolling = false;
                        BallHit = 0;
                        CushionHit = 0;
                        if (WasShotValid) { //scores the points

                            if (CurrentInningPlayer == PLAYER_A) {
                                PlayerAScore += 1;
                            } else {
                                PlayerBScore += 1;
                            }
                            if (PlayerBScore == PointsToWinGame || PlayerAScore == PointsToWinGame) {
                                GameOver = true;
                                InningStatus = true;
                            }
                            WasShotValid = false;

                        } else {
                            InningStatus = false;

                            if (!InningUpdatedAlready) { // if the inning has not been updated by the foul(); method already, update it
                                Inning += 1;
                                if (CurrentInningPlayer == PLAYER_A) {
                                    CurrentInningPlayer = PLAYER_B;
                                } else {
                                    CurrentInningPlayer = PLAYER_A;
                                }
                                if (CurrentBall == WHITE) {
                                    CurrentBall = YELLOW;
                                } else {
                                    CurrentBall = WHITE;
                                }
                            }
                            if (InningUpdatedAlready) { // resets the thing that makes the innings update or not
                                InningUpdatedAlready = false;
                            }
                        }
                    }
                }
            }
        }
    }
    /**
     * if called and the foul call was valid, switches the players and also adds the innings, tells endshot to not
     * run player switching and to not add innings if it has run before
     */
    public void foul() {
        if (HasCueStickStart = true) {
            if (!GameOver) {
                if (LagShotConfirmed) {
                    if (CurrentInningPlayer == PLAYER_A) {
                        CurrentInningPlayer = PLAYER_B;
                    } else {
                        CurrentInningPlayer = PLAYER_A;
                    }
                    if (CurrentBall == WHITE) {
                        CurrentBall = YELLOW;
                    } else {
                        CurrentBall = WHITE;
                    }
                    Inning += 1;
                    InningUpdatedAlready = true; // tells the end shot that it had already switched innings for this turn, do not do it
                    InningStatus = false;
                    WasShotValid = false;
                }
            }
        }
    }
    /**
     * Picks the players' balls and also who goes first
     *
     * @param selfBreak player chooses if they go first
     * @param cueBall   the ball the player picks
     */
    public void lagWinnerChooses(boolean selfBreak, BallType cueBall) {
        if (selfBreak) {
            CurrentInningPlayer = (LagWinnerStart);
            if (cueBall == WHITE) {
                CurrentBall = WHITE;
            } else if (cueBall == YELLOW) {
                CurrentBall = YELLOW;
            }
        } else {
            CurrentInningPlayer = (LagSecond);
            if (cueBall == WHITE) {
                CurrentBall = YELLOW;
            } else if (cueBall == YELLOW) {
                CurrentBall = WHITE;
            }
        }
        LagShotConfirmed = true; // tells the rest of the code it has run
    }
    /**
     * Gets the cueBall in play
     *
     * @return Current player's ball
     */
    public BallType getCueBall() {
        return CurrentBall;
    }
    /**
     * Gets the current game inning
     *
     * @return Inning count
     */
    public int getInning() {return Inning;}
    /**
     * Gets the current player
     *
     * @return The current player in the inning
     */
    public PlayerPosition getInningPlayer() {
        return CurrentInningPlayer;
    }
    /**
     * Gets A's score
     *
     * @return Player A's score
     */
    public int getPlayerAScore() {
        return PlayerAScore;
    }
    /**
     * Gets B's score
     *
     * @return Player B's score
     */
    public int getPlayerBScore() {
        return PlayerBScore;
    }
    /**
     * Sees if it is a bankshot
     *
     * @return If it is a bankshot
     */
    public boolean isBankShot() {return BankShot;}
    /**
     * Returns true or false depending on if it is the first shot of the game
     *
     * @return If it is breakshot
     */
    public boolean isBreakShot() {return IsItStillBreakshot;}
    /**
     * Checks if game is over
     *
     * @return If game is over
     */
    public boolean isGameOver() {
        return GameOver;
    }
    /**
     * Checks if inning is started
     *
     * @return Inning not started or started
     */
    public boolean isInningStarted() {return InningStatus;}
    /**
     * Sees if the shot happened
     *
     * @return If shot started
     */
    public boolean isShotStarted() {
        return shotStart;
    }
}



