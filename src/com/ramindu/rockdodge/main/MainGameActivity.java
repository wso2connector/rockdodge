package com.ramindu.rockdodge.main;

import java.io.IOException;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.engine.options.ConfigChooserOptions;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.util.FPSLogger;
import org.andengine.extension.augmentedreality.SimpleBaseAugmentedRealityGameActivity;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.bitmap.AssetBitmapTexture;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TextureRegionFactory;
import org.andengine.util.adt.color.Color;

import android.util.Log;
import android.view.Display;
import android.widget.Toast;

public class MainGameActivity extends SimpleBaseAugmentedRealityGameActivity {

	
	private float cameraWidth;
	private float cameraHeight;

	private ITexture mRockTexture;
	private ITextureRegion mRockTextureRegion;

	private Sprite rock;
	
	@SuppressWarnings("deprecation")
	@Override
	public EngineOptions onCreateEngineOptions() {
		final Display display = getWindowManager().getDefaultDisplay();
		cameraWidth = display.getWidth();
		cameraHeight = display.getHeight();

		final Camera camera = new Camera(0, 0, cameraWidth, cameraHeight);

		final EngineOptions engineOptions = new EngineOptions(true,
				ScreenOrientation.LANDSCAPE_SENSOR, new RatioResolutionPolicy(
						cameraWidth, cameraHeight), camera);
		final ConfigChooserOptions configChooserOptions = engineOptions
				.getRenderOptions().getConfigChooserOptions();
		configChooserOptions.setRequestedRedSize(8);
		configChooserOptions.setRequestedGreenSize(8);
		configChooserOptions.setRequestedBlueSize(8);
		configChooserOptions.setRequestedAlphaSize(8);
		configChooserOptions.setRequestedDepthSize(16);
		return engineOptions;
	}

	@Override
	public void onCreateResources() throws IOException {
		mRockTexture = new AssetBitmapTexture(getTextureManager(), getAssets(),
				"gfx/rock.png");
		mRockTextureRegion = TextureRegionFactory.extractFromTexture(mRockTexture);
		mRockTexture.load();
	}

	@Override
	public Scene onCreateScene() {
		mEngine.registerUpdateHandler(new FPSLogger());

		Scene scene = new Scene();
		scene.getBackground().setColor(Color.TRANSPARENT);

		final float centerX = cameraWidth / 2;
		final float centerY = cameraHeight / 2;

		rock = new Sprite(200, 200,
				mRockTextureRegion, this.getVertexBufferObjectManager());
		/*rock.registerEntityModifier(new MoveModifier(30, 0, 0, cameraWidth,
				cameraHeight));*/
		rock.setScale(0.5f);
		scene.registerUpdateHandler(new IUpdateHandler() {
			
			@Override
			public void reset() {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onUpdate(float pSecondsElapsed) {
				rock.setScale(1 * pSecondsElapsed);
				Log.i("Seconds Elapsed",String.valueOf(pSecondsElapsed));
				
			}
		});
		scene.attachChild(rock);

		return scene;
	}

	// ===========================================================
	// Methods
	// ===========================================================

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}