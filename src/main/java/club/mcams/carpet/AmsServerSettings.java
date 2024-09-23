package club.mcams.carpet;

import club.mcams.carpet.settings.RecipeRule;
import club.mcams.carpet.settings.Rule;

import club.mcams.carpet.observers.rule.recipeRule.RecipeRuleObserver;
import club.mcams.carpet.observers.rule.fancyFakePlayerName.FancyFakePlayerNameRuleObserver;

import club.mcams.carpet.validators.rule.blockChunkLoaderTimeController.MaxTimeValidator;
import club.mcams.carpet.validators.rule.commandPlayerChunkLoadController.MaxRangeValidator;
import club.mcams.carpet.validators.rule.enhancedWorldEater.BlastResistanceValidator;
import club.mcams.carpet.validators.rule.maxClientInteractionReachDistance.MaxClientInteractionReachDistanceValidator;
import club.mcams.carpet.validators.rule.maxPlayerBlockInteractionRange.MaxPlayerBlockInteractionRangeValidator;
import club.mcams.carpet.validators.rule.maxPlayerEntityInteractionRange.MaxPlayerEntityInteractionRangeValidator;

import top.byteeeee.annotationtoolbox.annotation.GameVersion;

import static carpet.settings.RuleCategory.*;
import static club.mcams.carpet.settings.AmsRuleCategory.*;

public class AmsServerSettings {

    @Rule(categories = {AMS, FEATURE, SURVIVAL})
    public static boolean softObsidian = false;

    @Rule(categories = {AMS, FEATURE, SURVIVAL, EXPERIMENTAL})
    public static boolean amsUpdateSuppressionCrashFix = false;

    @Rule(
        options = {"none", "minecraft:bone_block", "minecraft:diamond_ore", "minecraft:magma_block"},
        categories = {AMS, FEATURE},
        strict = false
    )
    public static String customBlockUpdateSuppressor = "none";

    @Rule(categories = {AMS, FEATURE})
    public static boolean superBow = false;

    @Rule(categories = {AMS, OPTIMIZATION})
    public static boolean optimizedDragonRespawn = false;

    @Rule(categories = {AMS, FEATURE})
    public static boolean netherWaterPlacement = false;

    @Rule(categories = {AMS, FEATURE, TNT})
    public static boolean blowUpEverything = false;

    @Rule(categories = {AMS, FEATURE, SURVIVAL})
    public static boolean sharedVillagerDiscounts = false;

    @Rule(
        options = {"false", "true", "minecraft:overworld", "minecraft:the_end", "minecraft:the_nether", "minecraft:the_end,minecraft:the_nether"},
        categories = {AMS, FEATURE},
        strict = false
    )
    public static String fakePeace = "false";

    @Rule(categories = {AMS, FEATURE, SURVIVAL})
    public static boolean extinguishedCampfire = false;

    @Rule(categories = {AMS, FEATURE})
    public static boolean campfireSmokeParticleDisabled = false;

    @Rule(categories = {AMS, FEATURE, SURVIVAL})
    public static boolean safeFlight = false;

    @Rule(categories = {AMS, FEATURE})
    public static boolean invulnerable = false;

    @Rule(categories = {AMS, FEATURE, SURVIVAL})
    public static boolean infiniteTrades = false;

    @Rule(categories = {AMS, FEATURE, CREATIVE})
    public static boolean creativeOneHitKill = false;

    @Rule(categories = {AMS, FEATURE, OPTIMIZATION})
    public static boolean bambooModelNoOffset = false;

    @Rule(categories = {AMS, FEATURE})
    public static boolean bambooCollisionBoxDisabled = false;

    @Rule(categories = {AMS, FEATURE})
    public static boolean antiFireTotem = false;

    @Rule(categories = {AMS, FEATURE, TNT})
    public static boolean itemAntiExplosion = false;

    @Rule(categories = {AMS, FEATURE, CREATIVE})
    public static boolean creativeShulkerBoxDropsDisabled = false;

    @Rule(categories = {AMS, FEATURE, CREATIVE})
    public static boolean bedRockFlying = false;

    @Rule(categories = {AMS, FEATURE, SURVIVAL})
    public static boolean shulkerHitLevitationDisabled = false;

    @Rule(categories = {AMS, FEATURE, SURVIVAL})
    public static boolean immuneShulkerBullet = false;

    @Rule(categories = {AMS, FEATURE, EXPERIMENTAL})
    public static blueSkullProbability blueSkullController = blueSkullProbability.VANILLA;

    @Rule(categories = {AMS, FEATURE, EXPERIMENTAL})
    public static boolean endermanTeleportRandomlyDisabled = false;

    @Rule(
        options = {"VANILLA", "Ⅰ", "Ⅱ", "Ⅲ", "Ⅳ", "Ⅴ"},
        categories = {AMS, FEATURE, EXPERIMENTAL}
    )
    public static String fasterMovement = "VANILLA";

    @Rule(categories = {AMS, FEATURE, EXPERIMENTAL})
    public static fasterMovementDimension fasterMovementController = fasterMovementDimension.ALL;

    @Rule(categories = {AMS, FEATURE, SURVIVAL})
    public static boolean easyWitherSkeletonSkullDrop = false;

    @Rule(categories = {AMS, FEATURE, SURVIVAL, COMMAND})
    public static boolean preventAdministratorCheat = false;

    @Rule(
        options = {"0", "1", "2", "3", "4", "ops", "true", "false"},
        categories = {AMS, FEATURE, SURVIVAL, COMMAND}
    )
    public static String commandAnvilInteractionDisabled = "false";

    @GameVersion(version = "Minecraft < 1.19.3")
    @Rule(categories = {AMS, FEATURE, SURVIVAL, EXPERIMENTAL, BUGFIX})
    public static boolean ghastFireballExplosionDamageSourceFix = false;

    @Rule(categories = {AMS, FEATURE, SURVIVAL})
    public static boolean cakeBlockDropOnBreak = false;

    @Rule(categories = {AMS, FEATURE, SURVIVAL})
    public static boolean noCakeEating = false;

    @Rule(categories = {AMS, FEATURE})
    public static boolean redstoneComponentSound = false;

    @Rule(
        options = {"-1"},
        categories = {AMS, FEATURE, SURVIVAL},
        validators = MaxPlayerBlockInteractionRangeValidator.class,
        strict = false
    )
    public static double maxPlayerBlockInteractionRange = -1.0D;

    @Rule(
        options = {"-1"},
        categories = {AMS, FEATURE, SURVIVAL},
        validators = MaxPlayerEntityInteractionRangeValidator.class,
        strict = false
    )
    public static double maxPlayerEntityInteractionRange = -1.0D;

    @Rule(
        options = {"-1"},
        categories = {AMS, FEATURE, SURVIVAL},
        validators = MaxClientInteractionReachDistanceValidator.class,
        strict = false
    )
    public static double maxClientInteractionReachDistance = -1.0D;

    @Rule(
        options = {"0", "1", "2", "3", "4", "ops", "true", "false"},
        categories = {AMS, FEATURE, COMMAND}
    )
    public static String commandCustomMovableBlock = "false";

    @Rule(categories = {AMS, FEATURE})
    public static boolean easyMaxLevelBeacon = false;

    @Rule(
        options = {"0", "1", "2", "3", "4", "ops", "true", "false"},
        categories = {AMS, FEATURE, SURVIVAL, TNT, COMMAND}
    )
    public static String commandCustomBlockBlastResistance = "false";

    @Rule(categories = {AMS, FEATURE})
    public static boolean regeneratingDragonEgg = false;

    @Rule(
        options = {"-1"},
        categories = {AMS, SURVIVAL, FEATURE, TNT},
        validators = BlastResistanceValidator.class,
        strict = false
    )
    public static double enhancedWorldEater = -1.0D;

    @Rule(categories = {AMS, FEATURE})
    public static boolean sneakToEditSign = false;

    @Rule(
        options = {"false", "bot", "fake_player"},
        categories = {AMS, FEATURE},
        validators = FancyFakePlayerNameRuleObserver.class,
        strict = false
    )
    public static String fancyFakePlayerName = "false";

    @Rule(categories = {AMS, FEATURE})
    public static boolean fakePlayerNoScoreboardCounter = false;

    @Rule(categories = {AMS, FEATURE, SURVIVAL})
    public static boolean noFamilyPlanning = false;

    @Rule(categories = {AMS, FEATURE})
    public static boolean hopperSuctionDisabled = false;

    @Rule(categories = {AMS, FEATURE, SURVIVAL})
    public static boolean noEnchantedGoldenAppleEating = false;

    @Rule(categories = {AMS, FEATURE, SURVIVAL})
    public static boolean useItemCooldownDisabled = false;

    @Rule(
        options = {"0", "1", "2", "3", "4", "5"},
        categories = {AMS, FEATURE, SURVIVAL}
    )
    public static int flippinCactusSoundEffect = 0;

    @Rule(categories = {AMS, FEATURE})
    public static boolean undyingCoral = false;

    @Rule(categories = {AMS, FEATURE, SURVIVAL})
    public static boolean enderDragonNoDestroyBlock = false;

    @Rule(categories = {AMS, FEATURE, SURVIVAL})
    public static boolean easyCompost = false;

    @Rule(categories = {AMS, FEATURE, SURVIVAL})
    public static boolean easyMineDragonEgg = false;

    @Rule(
        options = {"none", "apple", "stone"},
        categories = {AMS, FEATURE},
        strict = false
    )
    public static String breedableParrots = "none";

    @Rule(categories = {AMS, FEATURE, SURVIVAL})
    public static boolean kirinArm = false;

    @Rule(categories = {AMS, FEATURE, SURVIVAL})
    public static boolean sensibleEnderman = false;

    @Rule(categories = {AMS, FEATURE, SURVIVAL})
    public static boolean endermanPickUpDisabled = false;

    @Rule(categories = {AMS, FEATURE})
    public static boolean mitePearl = false;

    @Rule(categories = {AMS, FEATURE})
    public static boolean enderPearlSoundEffect = false;

    @Rule(
        options = {"0", "1", "2", "3", "4", "ops", "true", "false"},
        categories = {AMS, COMMAND}
    )
    public static String commandHere = "false";

    @Rule(
        options = {"0", "1", "2", "3", "4", "ops", "true", "false"},
        categories = {AMS, COMMAND}
    )
    public static String commandWhere = "false";

    @Rule(
        options = {"MainHandOnly", "NoPickUp", "false"},
        categories = {AMS, FEATURE, SURVIVAL}
    )
    public static String fakePlayerPickUpController = "false";

    @Rule(categories = {AMS, FEATURE, SURVIVAL})
    public static boolean sneakToEatCake = false;

    @Rule(
        options = {"0", "1", "2", "3", "4", "ops", "true", "false"},
        categories = {AMS, COMMAND}
    )
    public static String commandPlayerLeader = "false";

    @Rule(
        options = {"0", "1", "2", "3", "4", "ops", "true", "false"},
        categories = {AMS, COMMAND}
    )
    public static String commandPacketInternetGroper = "false";

    @Rule(categories = {AMS, FEATURE, SURVIVAL})
    public static boolean playerNoNetherPortalTeleport = false;

    @Rule(categories = {AMS, FEATURE, SURVIVAL})
    public static boolean infiniteDurability = false;

    @Rule(
        options = {"true", "false", "keepEndCrystal"},
        categories = {AMS, FEATURE, SURVIVAL}
    )
    public static String preventEndSpikeRespawn = "false";

    @Rule(categories = AMS)
    public static boolean welcomeMessage = false;

    @Rule(
        options = {"0", "1", "2", "3", "4", "ops", "true", "false"},
        categories = {AMS, COMMAND}
    )
    public static String commandGetSaveSize = "false";

    @Rule(categories = {AMS, FEATURE})
    public static boolean carpetAlwaysSetDefault = false;

    @Rule(categories = {AMS, FEATURE, SURVIVAL})
    public static boolean fertilizableSmallFlower = false;

    @Rule(categories = {AMS, FEATURE})
    public static boolean foliageGenerateDisabled = false;

    @Rule(
        options = {"0", "1", "2", "3", "4", "ops", "true", "false"},
        categories = {AMS, COMMAND}
    )
    public static String commandGetSystemInfo = "false";

    @Rule(categories = {AMS, FEATURE, SURVIVAL})
    public static boolean ironGolemNoDropFlower = false;

    @Rule(
        options = {"0", "1", "2", "3", "4", "ops", "true", "false"},
        categories = {AMS, COMMAND}
    )
    public static String commandGoto = "false";

    @Rule(
        options = {"false", "all", "realPlayerOnly", "fakePlayerOnly"},
        categories = {AMS, SURVIVAL}
    )
    public static String sendPlayerDeathLocation = "false";

    @Rule(categories = {AMS, FEATURE, SURVIVAL})
    public static boolean perfectInvisibility = false;

    @Rule(categories = {AMS, FEATURE, SURVIVAL})
    public static boolean sneakInvisibility = false;

    @Rule(
        options = {"0", "1", "2", "3", "4", "ops", "true", "false"},
        categories = {AMS, SURVIVAL, COMMAND}
    )
    public static String commandCustomCommandPermissionLevel = "false";

    @Rule(categories = {AMS, FEATURE, SURVIVAL})
    public static boolean shulkerGolem = false;

    @Rule(categories = {AMS, FEATURE, SURVIVAL})
    public static boolean headHunter = false;

    @Rule(
        options = {"0", "1", "2", "3", "4", "ops", "true", "false"},
        categories = {AMS, SURVIVAL, COMMAND}
    )
    public static String commandGetPlayerSkull = "false";

    @Rule(categories = {AMS, FEATURE, SURVIVAL})
    public static boolean quickVillagerLevelUp = false;

    @Rule(categories = {AMS, FEATURE, SURVIVAL})
    public static boolean strongLeash = false;

    @Rule(categories = {AMS, FEATURE, SURVIVAL})
    public static boolean superZombieDoctor = false;

    @Rule(categories = {AMS, FEATURE, SURVIVAL})
    public static boolean fakePlayerUseOfflinePlayerUUID = false;

    @Rule(categories = {AMS, FEATURE, SURVIVAL})
    public static boolean superLeash = false;

    @Rule(categories = {AMS, FEATURE, SURVIVAL})
    public static boolean fullMoonEveryDay = false;

    @Rule(categories = {AMS, FEATURE, SURVIVAL})
    public static boolean easyRefreshTrades = false;

    /*
     * 区块加载规则
     */
    @Rule(
        options = {"bone_block", "wither_skeleton_skull", "note_block", "false"},
        categories = {AMS, FEATURE, AMS_CHUNKLOADER}
    )
    public static String noteBlockChunkLoader = "false";

    @Rule(categories = {AMS, FEATURE, AMS_CHUNKLOADER})
    public static boolean bellBlockChunkLoader = false;

    @Rule(
        options = {"bone_block", "bedrock", "false"},
        categories = {AMS, FEATURE, AMS_CHUNKLOADER}
    )
    public static String pistonBlockChunkLoader = "false";

    @Rule(categories = {AMS, FEATURE, AMS_CHUNKLOADER})
    public static boolean blockChunkLoaderKeepWorldTickUpdate = false;

    @Rule(categories = {AMS, FEATURE, AMS_CHUNKLOADER})
    public static boolean keepWorldTickUpdate = false;

    @Rule(categories = {AMS, COMMAND, AMS_CHUNKLOADER})
    public static boolean commandPlayerChunkLoadController = false;

    @Rule(
        options = {"300"},
        categories = {AMS, FEATURE, AMS_CHUNKLOADER},
        validators = MaxTimeValidator.class,
        strict = false
    )
    public static int blockChunkLoaderTimeController = 300;

    @Rule(
        options = {"3"},
        categories = {AMS, FEATURE, AMS_CHUNKLOADER},
        validators = MaxRangeValidator.class,
        strict = false
    )
    public static int blockChunkLoaderRangeController = 3;

    /*
     * 合成表规则
     */
    @RecipeRule
    @Rule(categories = {AMS, CRAFTING, SURVIVAL}, validators = RecipeRuleObserver.class)
    public static boolean craftableEnchantedGoldenApples = false;

    @RecipeRule
    @Rule(categories = {AMS, CRAFTING, SURVIVAL}, validators = RecipeRuleObserver.class)
    public static boolean betterCraftableBoneBlock = false;

    @RecipeRule
    @Rule(categories = {AMS, CRAFTING, SURVIVAL}, validators = RecipeRuleObserver.class)
    public static boolean craftableElytra = false;

    @RecipeRule
    @Rule(categories = {AMS, CRAFTING, SURVIVAL}, validators = RecipeRuleObserver.class)
    public static boolean betterCraftableDispenser = false;

    public enum blueSkullProbability {
        VANILLA,
        SURELY,
        NEVER
    }

    public enum fasterMovementDimension {
        OVERWORLD,
        NETHER,
        END,
        ALL
    }
}
