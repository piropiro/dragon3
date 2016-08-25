package dragon3.edit;

import javax.inject.Singleton;

import dagger.Component;
import dragon3.edit.deploy.DeployEditor;

@Singleton
@Component(modules=EditorModule.class)
public interface EditorComponent {

	AnimeEditor getAnimeEditor();
	BodyEditor getBodyEditor();
	StageEditor getStageEditor();
	WazaEditor getWazaEditor();
	DeployEditor getDeployEditor();
}
