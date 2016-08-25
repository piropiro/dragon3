package card;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules=CardModule.class)
public interface CardComponent {
	void inject(CardDialog cd);
}
