public class Card {

	enum Suit {Club, Diamond, Heart, Spade};

	private Suit suit;
	private int rank;

	String rankname[] = { "A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K" };

	public Card(Suit s, int r) {
		suit = s;
		rank = r;
	}

	public void printCard() {
		//ndeck 幾副牌，suitname 花色，rankname 牌數字
		if (suit == Suit.Club) {
			System.out.println(Suit.Club + " , " + rankname[rank - 1]);
		}
		if (suit == Suit.Diamond) {
			System.out.println(Suit.Diamond + " , " + rankname[rank - 1]);
		}
		if (suit == Suit.Heart) {
			System.out.println(Suit.Heart + " , " + rankname[rank - 1]);
		}
		if (suit == Suit.Spade) {
			System.out.println(Suit.Spade + " , " + rankname[rank - 1]);
		}
	}

	public Suit getSuit() {
		return suit;
	}

	public int getRank() {
		return rank;
	}
}
