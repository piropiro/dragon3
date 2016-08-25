package dragon3.edit;

import dagger.Component;
import dragon3.edit.deploy.DeployEditor;

import javax.inject.Singleton;

@Singleton
@Component(modules=EditorModule.class)
public interface EditorComponent {

	AnimeEditor getAnimeEditor();
	BodyEditor getBodyEditor();
	StageEditor getStageEditor();
	WazaEditor getWazaEditor();
	DeployEditor getDeployEditor();
}
