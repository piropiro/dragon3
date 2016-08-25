package imo;



/**
 * Created by rara on 2016/08/19.
 */
public class ImoApp {
    public static void main(String[] args) {

		ImoComponent og = DaggerImoComponent.builder().build();
		//ObjectGraph og = ObjectGraph.create(new CardModule());

		ImoDialog id = new ImoDialog();
		og.inject(id);
		id.launch();
	}

}
