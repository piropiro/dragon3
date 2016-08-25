package card;


/**
 * Created by rara on 2016/08/19.
 */
public class CardApp {
    public static void main(String[] args) {

		CardComponent og = DaggerCardComponent.builder().build();
		//ObjectGraph og = ObjectGraph.create(new CardModule());

		CardDialog cd = new CardDialog();
		og.inject(cd);
		cd.launch();
	}

}
