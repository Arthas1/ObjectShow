package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.material.RenderState;
import com.jme3.math.ColorRGBA;
import static com.jme3.math.FastMath.PI;
import com.jme3.math.Vector3f;
import com.jme3.post.FilterPostProcessor;
import com.jme3.post.filters.FogFilter;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.util.TangentBinormalGenerator;

/**
 * @author 3D model by Artur Bardowski
 */
public class Main extends SimpleApplication {

    public static void main(String[] args) {
        Main app = new Main();
        app.start();

    }
    private Node supplyCrateNode;
    private Spatial modelSupplyCrate;
    private RigidBodyControl physicsSupplyCrate;
    private Node floorNode;
    private Spatial modelBackdrop;
    private FilterPostProcessor fpp;

    @Override
    public void simpleInitApp() {

// See below
// CRATE ---
        System.out.println("Art");
        supplyCrateNode = new Node("supply_crate");
        modelSupplyCrate = assetManager.loadModel("Models/crate1b/crate1b.j3o");
        TangentBinormalGenerator.generate(modelSupplyCrate);
        physicsSupplyCrate = new RigidBodyControl(0f);
        supplyCrateNode.setLocalTranslation(new Vector3f(0, -2, 6));
        supplyCrateNode.addControl(physicsSupplyCrate);
        supplyCrateNode.attachChild(modelSupplyCrate);
        rootNode.attachChild(supplyCrateNode);
 //  Shadow
        Spatial shadowCrate = assetManager.loadModel("Models/cien/cien.j3o");
        Material matCrate = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        matCrate.setTexture("ColorMap", assetManager.loadTexture("Models/cien/cien.png"));

        matCrate.setFloat("AlphaDiscardThreshold", 0.1f);
        shadowCrate.setQueueBucket(RenderQueue.Bucket.Transparent);
        matCrate.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);
        shadowCrate.setMaterial(matCrate);
        shadowCrate.setLocalScale(2f, 1.6f, 2f);
        supplyCrateNode.attachChild(shadowCrate);
// Floor
        floorNode = new Node("floor");
        modelBackdrop = assetManager.loadModel("Models/backdrop/backdrop.j3o");
        floorNode.setLocalTranslation(new Vector3f(0, -2.2f, -1));
        floorNode.addControl(physicsSupplyCrate);
        floorNode.attachChild(modelBackdrop);
        rootNode.attachChild(floorNode);
        
 //FOG
        fpp = new FilterPostProcessor(assetManager);
        FogFilter fog = new FogFilter();
         fog.setFogColor(new ColorRGBA(0.9f, 0.9f, 0.8f, 1.0f));
  

        fog.setFogDistance(40);
        fog.setFogDensity(2f);
        fpp.addFilter(fog);
        viewPort.addProcessor(fpp);
 
// Light     
   DirectionalLight sun = new DirectionalLight();
        sun.setColor(ColorRGBA.White);
        sun.setDirection(new Vector3f(-3f, -3f, -3));
        rootNode.addLight(sun);

    }

    @Override
    public void simpleUpdate(float tpf) {
        //TODO: add update code
             supplyCrateNode.rotate(0, PI * tpf / 20, 0);
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }
}
