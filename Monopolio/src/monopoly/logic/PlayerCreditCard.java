package monopoly.logic;

public class PlayerCreditCard
{
	public int money;
	
	public PlayerCreditCard(){
		money = 15000;
	}
	
	public void debit(int value){
		money -= value;
	}
	
	public void credit(int value){
		money += value;
	}
}
