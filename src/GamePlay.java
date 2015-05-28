public class GamePlay{
	long countDownEnd;
	long gameStart;
	int time;
	long pauseTime;
	public GamePlay(){
	}
	public void startCountDown(int secondsLater){
		countDownEnd = System.currentTimeMillis() + secondsLater * 1000;
	}
	public int timeLeft(){
		return  (int)((countDownEnd - System.currentTimeMillis()+500)/1000);
	}
	public void startGame(){
		gameStart = System.currentTimeMillis();
	}
	public int getGameTime(){
		return (int)((System.currentTimeMillis()-gameStart+500)/1000);
	}
	public void end(){
		time = getGameTime();
	}
	public int getWinTime(){
		return time;
	}
	public void pause(){
		pauseTime = (System.currentTimeMillis()-gameStart);
	}
	public void resume(){
		gameStart = System.currentTimeMillis()-pauseTime;
	}

}