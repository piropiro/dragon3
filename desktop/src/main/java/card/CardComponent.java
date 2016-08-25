package card;

import dagger.Component;

import javax.inject.Singleton;

@Singleton
@Component(modules=CardModule.class)
public interface CardComponent {
	void inject(CardDialog cd);
}
