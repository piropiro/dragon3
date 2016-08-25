package imo;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules= ImoModule.class)
public interface ImoComponent {
	void inject(ImoDialog cd);
}
