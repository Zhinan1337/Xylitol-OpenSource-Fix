//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.module;

import shop.xiaoda.module.modules.movement.Timer;
import shop.xiaoda.module.values.*;
import shop.xiaoda.*;
import sun.misc.*;
import org.lwjgl.opengl.*;
import java.lang.reflect.*;
import shop.xiaoda.module.modules.combat.*;
import shop.xiaoda.module.modules.render.*;
import shop.xiaoda.module.modules.misc.*;
import shop.xiaoda.module.modules.player.*;
import shop.xiaoda.module.modules.movement.*;
import shop.xiaoda.module.modules.world.*;
import java.util.function.*;
import java.util.*;
import shop.xiaoda.event.misc.*;
import shop.xiaoda.event.*;
import shop.xiaoda.event.rendering.*;

public class ModuleManager
{
    private final List<Module> modules;
    private boolean enabledNeededMod;
    
    public ModuleManager() {
        this.modules = new ArrayList<Module>();
        this.enabledNeededMod = true;
    }
    
    private void addModule(final Module module) {
        for (final Field field : module.getClass().getDeclaredFields()) {
            try {
                field.setAccessible(true);
                final Object obj = field.get(module);
                if (obj instanceof Value) {
                    module.getValues().add((Value<?>) obj);
                }
            }
            catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        this.modules.add(module);
    }
    
    public List<Module> getModules() {
        return this.modules;
    }
    
    public <T extends Module> T getModule(final Class<T> cls) {
        for (final Module m : this.modules) {
            if (m.getClass() == cls) {
                return (T)m;
            }
        }
        return null;
    }
    
    public Module getModule(final String name) {
        for (final Module m : this.modules) {
            if (m.getName().equalsIgnoreCase(name)) {
                return m;
            }
        }
        return null;
    }
    
    public void init() {
        EventManager.register(this);
        System.out.println("Init Modules...");
        this.addModule(new AutoClicker());
        this.addModule(new AntiBot());
        this.addModule(new AutoSoup());
        this.addModule(new AutoWeapon());
        this.addModule(new BackTrack());
        this.addModule(new Criticals());
        this.addModule(new KillAura());
        this.addModule(new Reach());
        this.addModule(new SuperKnockback());
        this.addModule(new Velocity());
        this.addModule(new BowAimbot());
        this.addModule(new TimerRange());
        this.addModule(new AutoClip());
        this.addModule(new NoSlow());
        this.addModule(new Speed());
        this.addModule(new FastLadder());
        this.addModule(new Sprint());
        this.addModule(new Step());
        this.addModule(new Fly());
        this.addModule(new Strafe());
        this.addModule(new Timer());
        this.addModule(new TargetStrafe());
        this.addModule(new GuiMove());
        this.addModule(new WallClimb());
        this.addModule(new BlockESP());
        this.addModule(new BlockHit());
        this.addModule(new Camera());
        this.addModule(new Chams());
        this.addModule(new ClickGui());
        this.addModule(new ChinaHat());
        this.addModule(new ESP());
        this.addModule(new GlowESP());
        this.addModule(new NameTags());
        this.addModule(new FullBright());
        this.addModule(new Health());
        this.addModule(new HUD());
        this.addModule(new ItemPhysics());
        this.addModule(new Skeletal());
        this.addModule(new KillEffect());
        this.addModule(new PostProcessing());
        this.addModule(new MotionBlur());
        this.addModule(new Trail());
        this.addModule(new XRay());
        this.addModule(new Projectile());
        this.addModule(new MoBendsMod());
        this.addModule(new Ambience());
        this.addModule(new AutoLobby());
        this.addModule(new AutoPlay());
        this.addModule(new Disabler());
        this.addModule(new FakePlayer());
        this.addModule(new MemoryFix());
        this.addModule(new Protocol());
        this.addModule(new PingSpoof());
        this.addModule(new ModuleHelper());
        this.addModule(new NoRotateSet());
        this.addModule(new NoPitchLimit());
        this.addModule(new Teams());
        this.addModule(new GrimAC());
        this.addModule(new AntiVoid());
        this.addModule(new AntiFireBall());
        this.addModule(new AutoArmor());
        this.addModule(new AutoTool());
        this.addModule(new Blink());
        this.addModule(new FastPlace());
        this.addModule(new InvCleaner());
        this.addModule(new NoFall());
        this.addModule(new FastEat());
        this.addModule(new Regen());
        this.addModule(new SpeedMine());
        this.addModule(new ChestStealer());
        this.addModule(new NoWater());
        this.addModule(new NoWeb());
        this.addModule(new CivBreak());
        this.addModule(new Scaffold());
        this.addModule(new Eagle());
        this.addModule(new PlayerWarn());
        this.addModule(new ChestAura());
        this.addModule(new Stuck());
        this.addModule(new AutoPearl());
        this.modules.sort(Comparator.comparing((Function<? super Module, ? extends Comparable>)Module::getName));
    }
    
    public boolean haveModules(final Category category, final String key) {
        final ArrayList<Module> array = new ArrayList<Module>(this.modules);
        array.removeIf(module -> module.getCategory() != category);
        array.removeIf(module -> !module.getName().toLowerCase().replaceAll(" ", "").contains(key));
        return array.size() == 0;
    }
    
    @EventTarget
    public void onKey(final EventKey e) {
        for (final Module m : this.modules) {
            if (m.getKey() == e.getKey() && e.getKey() != -1) {
                m.toggle();
            }
        }
    }
    
    public List<Module> getModsByCategory(final Category m) {
        final List<Module> findList = new ArrayList<Module>();
        for (final Module mod : this.modules) {
            if (mod.getCategory() == m) {
                findList.add(mod);
            }
        }
        return findList;
    }
    
    @EventTarget
    private void on2DRender(final EventRender2D e) {
        if (this.enabledNeededMod) {
            this.enabledNeededMod = false;
            for (final Module m : this.modules) {
                if (!m.isDefaultOn()) {
                    continue;
                }
                m.setState(true);
            }
        }
    }
}
