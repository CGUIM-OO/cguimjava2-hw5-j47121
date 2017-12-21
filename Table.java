import java.util.ArrayList;

public class Table {
	public static final int MAXPLAYER = 4;
	private Deck Deck;
	private Player[] Player;
	private Dealer Dealer;
	private int[] pos_betArray = new int[MAXPLAYER]; //定義一個Array來儲存玩家下的bet並初始化
	private ArrayList<Card> oneRoundCard;
	private int nDecks;
	
	public Table(int nDecks) {
		this.nDecks = nDecks;
		Deck = new Deck(nDecks);
		Player = new Player[MAXPLAYER];
	}
	
	public void set_player(int pos, Player p) {
		Player[pos]=p;
	}
	
	public Player[] get_player() {
		return Player;
	}

	public void set_dealer(Dealer d) {
		Dealer = d;
	}
	
	public Card get_face_up_card_of_dealer() {
		return Dealer.getOneRoundCard().get(1); //取的莊家開著的牌
	}
		
	private void ask_each_player_about_bets() {
		for(int i = 0; i < MAXPLAYER; i++) {
			Player[i].sayHello();
			pos_betArray[i] = Player[i].makeBet(); //將玩家下的bet放進Array
		}
	}

	private void distribute_cards_to_dealer_and_players() {  //因為玩家看的到自己的牌所以都為true
		for(int i = 0; i < MAXPLAYER; i++) {
			ArrayList<Card> playercard = new ArrayList<Card>();
			playercard.add(Deck.getOneCard(true));
			playercard.add(Deck.getOneCard(true));
			Player[i].setOneRoundCard(playercard);
		}
		ArrayList<Card> dealercard = new ArrayList<Card>();  
		dealercard.add(Deck.getOneCard(false));    //因為玩家看不到莊家蓋著的牌所以為false
		dealercard.add(Deck.getOneCard(true));
		Dealer.setOneRoundCard(dealercard);
		System.out.print("Dealer's face up card is ");
		Card c = get_face_up_card_of_dealer();
		c.printCard();
	} 
	
	private void ask_each_player_about_hits() {   
		for (int i = 0; i < MAXPLAYER; i++) {
			boolean hit = false;
			System.out.print(Player[i].getName() + "'s Cards now:");  //先輸出玩家一開始拿到的兩張牌
			Player[i].printAllCard();
			do {
				hit = Player[i].hit_me(this);
				if (hit) {  //詢問各玩家是否還需要牌
					ArrayList<Card> playercard = new ArrayList<Card>();
					playercard = Player[i].getOneRoundCard();
					playercard.add(Deck.getOneCard(true));
					Player[i].setOneRoundCard(playercard);
					System.out.print("Hit!");
					System.out.print(Player[i].getName() + "'s Cards now:");
					for (Card c : Player[i].getOneRoundCard()) {  //print出玩家拿的牌
						c.printCard();
					}
				} else {
					System.out.println(Player[i].getName() + ", Pass hit!");
					System.out.println(Player[i].getName() + "'s hit is over!");
				}
			} while (hit);  //直到玩家不再要牌
			
		}
	}

	private void ask_dealer_about_hits() {  //莊家是否要牌
		boolean hit = false;
		do {
			hit = Dealer.hit_me(this);
			if (hit) {
				ArrayList<Card> dealercard = new ArrayList<Card>();
				dealercard = Dealer.getOneRoundCard();
				dealercard.add(Deck.getOneCard(true));
				Dealer.setOneRoundCard(dealercard);
				System.out.print("Hit!");
				System.out.print("Dealer's Cards now:");
				for (Card c : Dealer.getOneRoundCard()) {
					c.printCard();
				}
			} else {
				System.out.println("Dealer's hit is over!");
				System.out.print("Dealer's Cards now:");
				for (Card c : Dealer.getOneRoundCard()) {
					c.printCard();
				}
			}
		}
		while(hit);
	}

	
	private void calculate_chips() {
		int chips;
		chips = Dealer.getTotalValue();
		System.out.println("Dealer's card value is " + chips + " ,Cards:");
		Dealer.printAllCard();
		for(int i = 0; i < MAXPLAYER; i++) {
			Player[i].getTotalValue();
			System.out.println(Player[i].getName() + "'s Card: ");
			Player[i].printAllCard();
			System.out.print(Player[i].getName() + "'s card value is " + Player[i].getTotalValue());
			
			if(Player[i].getTotalValue() > 21 && Dealer.getTotalValue() <= 21) {  //如果玩家爆掉
				Player[i].increaseChips(-pos_betArray[i]);
				System.out.print(", Loss " + pos_betArray[i] + " Chips,the Chips now is: ");
			}
			else if(Player[i].getTotalValue() <= 21 && Dealer.getTotalValue() > 21) {  //如果莊家爆掉
				Player[i].increaseChips(pos_betArray[i]);
				System.out.print(", Get " + pos_betArray[i] + " Chips,the Chips now is: ");
			}
			else if(Player[i].getTotalValue() > Dealer.getTotalValue() && Player[i].getTotalValue() <= 21) {  //雙方皆小於等於21，但玩家點數大於莊家
				Player[i].increaseChips(pos_betArray[i]);
				System.out.print(", Get " + pos_betArray[i] + " Chips,the Chips now is: ");
			}
			else if(Player[i].getTotalValue() > Dealer.getTotalValue() && Dealer.getTotalValue() <= 21) {  //雙方皆小於等於21，但玩家點數小於莊家
				Player[i].increaseChips(-pos_betArray[i]);
				System.out.println(", Loss " + pos_betArray[i] + " Chips,the Chips now is: ");
			}
			else {  //平手則錢不會改變
				System.out.print(",chips have no change!" + " Chips,the Chips now is: ");
			}
			System.out.println(Player[i].getCurrentChips());
		}	
	}
	
	public int[] get_palyers_bet() {
		return pos_betArray;
	}
	
	public void play() {
		ask_each_player_about_bets();
		distribute_cards_to_dealer_and_players();
		ask_each_player_about_hits();
		ask_dealer_about_hits();
		calculate_chips();
	}
}