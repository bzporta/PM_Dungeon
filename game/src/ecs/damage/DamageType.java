package ecs.damage;

import java.io.Serializable;

/** Type of damage to include resistances and vulnerabilities in the damage calculation. */
public enum DamageType implements Serializable {
    PHYSICAL,
    MAGIC,
    FIRE
}
