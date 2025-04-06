package generator;

import java.io.Serializable;
import java.util.Random;

public class OptionBank implements Serializable {

    // Private constructor to prevent instantiation
    private OptionBank() {}

    // Nested Enum for FirstName with 100 words
    public enum FirstName {
        JOHN, MARY, ALICE, BOB, CHARLIE, DAVID, EMILY, FRANK, GEORGE, HANNAH,
        LUCY, MARK, NINA, OLIVER, PETER, QUEENIE, RACHEL, SAM, TINA, URSULA,
        VICTOR, WENDY, XANDER, YASMIN, ZOE, ABBY, BEN, CASSIE, DANIEL, ELENA,
        FELIX, GRACE, HARRY, IRENE, JACK, KATE, LEO, MEGAN, NOAH, OSCAR,
        PAULA, QUINN, RON, SUE, TOM, UMA, VERA, WILL, XENA, YURI, ZARA,
        ALAN, BETTY, CARL, DORA, ELI, FIONA, GINA, HAL, IVY, JANE,
        URBAN, VIOLET, WALT, XIMENA, YOLANDA, ZEB, AIDAN, BLAKE, CLAIRE, DYLAN,
        ERIC, FRED, GAVIN, HEIDI, ISAAC, JOY, KEN, LANA, MILES, NORA;

        private static final Random RANDOM = new Random();

        public static FirstName getRandom() {
            FirstName[] values = values();
            return values[RANDOM.nextInt(values.length)];
        }
    }

    // Nested Enum for LastName with 100 words
    public enum LastName {
        SMITH, JOHNSON, WILLIAMS, BROWN, JONES, MILLER, DAVIS, GARCIA, RODRIGUEZ, MARTINEZ,
        HERNANDEZ, LOPEZ, GONZALEZ, WILSON, ANDERSON, THOMAS, TAYLOR, MOORE, JACKSON, LEE,
        PEREZ, THOMPSON, WHITE, HARRIS, SANCHEZ, CLARK, RAMIREZ, LEWIS, ROBINSON, WALKER,
        YOUNG, ALLEN, KING, WRIGHT, SCOTT, TORRES, NGUYEN, HILL, FLORES, GREEN,
        ADAMS, NELSON, BAKER, HALL, RIVERA, CAMPBELL, MITCHELL, CARTER, ROBERTS, GOMEZ,
        PHILLIPS, EVANS, TURNER, DIAZ, PARKER, CRUZ, EDWARDS, COLLINS, REYES, STEWART,
        MORRIS, MORALES, MURPHY, COOK, ROGERS, GUTIERREZ, ORTIZ, MORGAN, COOPER, PETERSON,
        BAILEY, REED, KELLY, HOWARD, RAMOS, KIM, COX, WARD, RICHARDSON, WATSON,
        BROOKS, CHAVEZ, WOOD, JAMES, BENNETT, GRAY, MENDOZA, RUIZ, HUGHES, PRICE;

        private static final Random RANDOM = new Random();

        public static LastName getRandom() {
            LastName[] values = values();
            return values[RANDOM.nextInt(values.length)];
        }
    }

    // Nested Enum for Address with 100 words
    public enum Address {
        MAIN_STREET, HIGH_STREET, OAK_AVENUE, MAPLE_DRIVE, PINE_COURT, CEDAR_LANE, ELM_STREET, ASH_STREET, BIRCH_LANE, SPRUCE_DRIVE,
        WILLOW_ROAD, CHERRY_LANE, DOGWOOD_DRIVE, FIR_AVENUE, POPLAR_COURT, WALNUT_LANE, CHESTNUT_DRIVE, HAWTHORNE_ROAD, LILAC_LANE, MAGNOLIA_DRIVE,
        CYPRESS_STREET, OLIVE_ROAD, PALM_DRIVE, SYCAMORE_LANE, IVY_COURT, JUNIPER_ROAD, COTTONWOOD_LANE, SAGE_DRIVE, LINDEN_ROAD, HONEYSUCKLE_LANE,
        MOSSY_ROAD, CLOVER_DRIVE, DAISY_LANE, ROSEWOOD_COURT, PEONY_ROAD, AZALEA_DRIVE, BUTTERCUP_LANE, ORCHID_COURT, TULIP_DRIVE, VIOLET_LANE,
        LAVENDER_DRIVE, GERANIUM_LANE, PANSY_COURT, MARIGOLD_ROAD, SNAPDRAGON_DRIVE, PETUNIA_LANE, BEGONIA_COURT, BLUEBELL_ROAD, LUPINE_DRIVE, SUNFLOWER_LANE,
        MEADOW_ROAD, POND_DRIVE, GLEN_LANE, GROVE_ROAD, VALLEY_DRIVE, CANYON_LANE, RIDGE_ROAD, RIVER_DRIVE, STREAM_LANE, CREEK_ROAD,
        BLUFF_DRIVE, HARBOR_LANE, BAY_ROAD, POINT_DRIVE, SHORE_LANE, BEACH_ROAD, SANDY_DRIVE, DUNE_LANE, WINDY_ROAD, BREEZE_DRIVE,
        CLEARWATER_LANE, MOUNTAIN_ROAD, FOOTHILL_DRIVE, PEAK_LANE, SUMMIT_ROAD, PINECREST_DRIVE, HILLTOP_LANE, WOODLAND_ROAD, FOREST_DRIVE, GLACIER_LANE,
        ISLAND_ROAD, SEASIDE_DRIVE, OCEANVIEW_LANE, LIGHTHOUSE_ROAD, SUNSET_DRIVE, HORIZON_LANE, DAWN_ROAD, TWILIGHT_DRIVE, SKYLINE_LANE, STARGAZER_ROAD;

        private static final Random RANDOM = new Random();

        public static Address getRandom() {
            Address[] values = values();
            return values[RANDOM.nextInt(values.length)];
        }
    }

    // Nested Enum for Hospital Physical Locations
    public enum HospitalLocation {
        MAIN_ENTRANCE,
        PARKING_LOT,
        EASTERN_WING,
        WESTERN_WING,
        NORTHERN_WING,
        SOUTHERN_WING,
        CAFETERIA,
        LOBBY,
        GARDEN,
        ROOFTOP_HELIPAD;

        private static final Random RANDOM = new Random();

        public static HospitalLocation getRandom() {
            HospitalLocation[] values = values();
            return values[RANDOM.nextInt(values.length)];
        }
    }
    
    

    public enum FractureLocation {
        SKULL, CLAVICLE, HUMERUS, RADIUS, ULNA, RIBS, SPINE, PELVIS, FEMUR, TIBIA,
        SCAPULA, STERNUM, MANDIBLE, ZYGOMATIC_BONE, NASAL_BONE, MAXILLA, PATELLA, FIBULA, METACARPAL, PHALANX,
        TALUS, CALCANEUS, METATARSAL, CUBOID, NAVICULAR, CUNEIFORM, SACRUM, COCCYX, PARIETAL_BONE, TEMPORAL_BONE,
        OCCIPITAL_BONE, MALLEOLUS, TRAPEZIUM, TRAPEZOID, LUNATE, PISIFORM, HAMATE, CAPITATE, SCAPHOID, SESAMOID;

        private static final Random RANDOM = new Random();

        public static FractureLocation getRandom() {
            FractureLocation[] values = values();
            return values[RANDOM.nextInt(values.length)];
        }
    }


    public enum InjuryLocation {
        SCALP_LACERATION, CERVICAL_SPRAIN, ACROMIOCLAVICULAR_JOINT_INJURY, LATERAL_EPICONDYLITIS, CARPAL_TUNNEL_SYNDROME, METACARPAL_CONTUSION,
        SACROILIAC_JOINT_DYSFUNCTION, PATELLAR_TENDINITIS, ACHILLES_TENDINOPATHY, PLANTAR_FASCIITIS;

        private static final Random RANDOM = new Random();

        public static InjuryLocation getRandom() {
            InjuryLocation[] values = values();
            return values[RANDOM.nextInt(values.length)];
        }
    }

    // Nested Enum for Diseases
    public enum Disease {
        DIABETES, HYPERTENSION, ASTHMA, CHRONIC_OBSTRUCTIVE_PULMONARY_DISEASE, ALZHEIMERS_DISEASE, PARKINSONS_DISEASE,
        CORONARY_ARTERY_DISEASE, HEPATITIS_C, PNEUMONIA, CHOLESTEROL_EMBOLISM, RHEUMATOID_ARTHRITIS, SYSTEMIC_LUPUS_ERYTHEMATOSUS,
        CROHNS_DISEASE, ULCERATIVE_COLITIS, MULTIPLE_SCLEROSIS, CELIAC_DISEASE, GLAUCOMA, MACULAR_DEGENERATION, GOUT, ANEMIA;

        private static final Random RANDOM = new Random();

        public static Disease getRandom() {
            Disease[] values = values();
            return values[RANDOM.nextInt(values.length)];
        }
    }

    // Nested Enum for Fractures
    public enum Fracture {
        SKULL_FRACTURE, CLAVICLE_FRACTURE, HUMERUS_FRACTURE, RADIUS_FRACTURE, ULNA_FRACTURE, RIB_FRACTURE, SPINAL_FRACTURE,
        PELVIC_FRACTURE, FEMUR_FRACTURE, TIBIA_FRACTURE, FIBULA_FRACTURE, PATELLA_FRACTURE, METATARSAL_FRACTURE, PHALANGEAL_FRACTURE;

        private static final Random RANDOM = new Random();

        public static Fracture getRandom() {
            Fracture[] values = values();
            return values[RANDOM.nextInt(values.length)];
        }
    }

    // Nested Enum for Injuries
    public enum Injury {
        CONCUSSION, SPRAINED_ANKLE, SHOULDER_DISLOCATION, WHIPLASH, TENNIS_ELBOW, ACHILLES_TENDON_RUPTURE,
        CARPAL_TUNNEL_SYNDROME, HAMSTRING_STRAIN, ROTATOR_CUFF_TEAR, ACL_INJURY, MENISCUS_TEAR, LIGAMENT_SPRAIN,
        MUSCLE_CONTUSION, LACERATION, BURNS;

        private static final Random RANDOM = new Random();

        public static Injury getRandom() {
            Injury[] values = values();
            return values[RANDOM.nextInt(values.length)];
        }
    }

    // Nested Enum for Medications
    public enum Medication {
        PARACETAMOL, IBUPROFEN, AMOXICILLIN, ATORVASTATIN, OMEPRAZOLE, METFORMIN, LEVOTHYROXINE, SIMVASTATIN,
        AZITHROMYCIN, HYDROCHLOROTHIAZIDE, LISINOPRIL, GABAPENTIN, LOSARTAN, ALBUTEROL, AMLODIPINE, CLARITHROMYCIN,
        WARFARIN, PREDNISONE, MONTELUKAST, DEXAMETHASONE;

        private static final Random RANDOM = new Random();

        public static Medication getRandom() {
            Medication[] values = values();
            return values[RANDOM.nextInt(values.length)];
        }
    }

    // Nested Enum for Specialization
    public enum Specialization {
        NEUROLOGY, OTHER, CARDIOLOGY, OTOLARYNGOLOGY, ORTHOPEDICS, SURGERY, OPHTHALMOLOGY, PULMONOLOGY, INTENSIVE_CARE;

        private static final Random RANDOM = new Random();

        public static Specialization getRandom() {
            Specialization[] values = values();
            return values[RANDOM.nextInt(values.length)];
        }
    }

    // Nested Enum for BiologicalSex
    public enum BiologicalSex {
        M,  // Male
        F;  // Female

        private static final Random RANDOM = new Random();

        public static BiologicalSex getRandom() {
            BiologicalSex[] values = values();
            return values[RANDOM.nextInt(values.length)];
        }
    }

    public enum HealthFund {
        LEUMIT_HEALTH_CARE_SERVICES, CLALIT_HEALTH_SERVICES, MACCABI_HEALTH_SERVICES, KUPAT_HOLIM_MEUHEDET;

        private static final Random RANDOM = new Random();

        public static HealthFund getRandom() {
            HealthFund[] values = values();
            return values[RANDOM.nextInt(values.length)];
        }
    }

    public enum Treatment {
        ANTIBIOTIC_THERAPY_FOR_BACTERIAL_INFECTION, PHYSICAL_THERAPY_FOR_POST_SURGERY_REHABILITATION, CHEMOTHERAPY_FOR_CANCER_TREATMENT,
        DIALYSIS_FOR_KIDNEY_FAILURE, CORTICOSTEROID_INJECTION_FOR_JOINT_PAIN_RELIEF, BLOOD_TRANSFUSION_FOR_SEVERE_ANEMIA,
        INSULIN_THERAPY_FOR_DIABETES_MANAGEMENT, CARDIAC_CATHETERIZATION_FOR_CORONARY_ARTERY_DISEASE, RADIATION_THERAPY_FOR_TUMOR_REDUCTION,
        ANTIVIRAL_MEDICATION_FOR_VIRAL_INFECTION, ANTICOAGULANT_THERAPY_FOR_BLOOD_CLOT_PREVENTION, RESPIRATORY_THERAPY_FOR_CHRONIC_LUNG_DISEASE,
        SURGICAL_WOUND_CARE_AND_DRESSING_CHANGES, INTRAVENOUS_HYDRATION_FOR_DEHYDRATION, SPEECH_THERAPY_FOR_POST_STROKE_RECOVERY,
        IMMUNOTHERAPY_FOR_AUTOIMMUNE_DISEASE_MANAGEMENT, PAIN_MANAGEMENT_WITH_OPIOID_ANALGESICS, LIFESTYLE_COUNSELING_FOR_WEIGHT_MANAGEMENT,
        PROSTHETIC_FITTING_AND_REHABILITATION, VACCINATION_FOR_INFECTIOUS_DISEASE_PREVENTION;

        private static final Random RANDOM = new Random();

        public static Treatment getRandom() {
            Treatment[] values = values();
            return values[RANDOM.nextInt(values.length)];
        }
    }

    public enum HospitalDepartmentName {
        SUNSHINE_ALLEY,
        MOONLIT_BOULEVARD,
        STARLIGHT_PLAZA,
        MYSTIC_MEADOWS,
        DREAMWEAVER_HALL,
        FIREFLY_GROVE,
        WHISPERING_WOODS,
        RAINBOW_HORIZONS,
        WISHING_WELL_COURTYARD,
        CRYSTAL_GARDENS,
        PEACEFUL_PINES,
        GOLDEN_ACRES,
        SILVER_LAKE,
        TWILIGHT_VALE,
        ECHOES_CANYON,
        WINDSONG_VALLEY,
        HARMONY_COVE,
        CHARMING_HARBOR,
        ENCHANTED_RIDGE,
        TRANQUIL_PARK,
        CLOUD_NINE_TERRACE;

        private static final Random RANDOM = new Random();

        public static HospitalDepartmentName getRandom() {
            HospitalDepartmentName[] values = values();
            return values[RANDOM.nextInt(values.length)];
        }
    }
    
    public enum Gender {
        MALE, FEMALE, NON_BINARY, GENDERQUEER, GENDERFLUID, AGENDER, BIGENDER, TWO_SPIRIT, DEMIBOY, DEMIGIRL,
        ANDROGYNE, TRANS_MALE, TRANS_FEMALE, CISGENDER_MALE, CISGENDER_FEMALE, GENDER_NONCONFORMING, INTERSEX, PANGENDER, NEUTROIS, MAVERIQUE,
        POLYGENDER, OMNIGENDER, THIRD_GENDER, AGENDER_FLUID, DEMIGENDER, QUESTIONING, AFAB_NON_BINARY, AMAB_NON_BINARY, AROGENDER, LIBRAFEMME;

        private static final Random RANDOM = new Random();

        public static Gender getRandom() {
            Gender[] values = values();
            return values[RANDOM.nextInt(values.length)];
        }
    }

    public static FirstName getRandomFirstName() {
        return FirstName.getRandom();
    }

    public static LastName getRandomLastName() {
        return LastName.getRandom();
    }

    public static Address getRandomAddress() {
        return Address.getRandom();
    }

    public static Gender getRandomGender() {
        return Gender.getRandom();
    }
}
