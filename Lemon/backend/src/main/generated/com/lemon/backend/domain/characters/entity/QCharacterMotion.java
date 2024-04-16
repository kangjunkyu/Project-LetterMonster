<<<<<<<< HEAD:Lemon/backend/src/main/generated/com/lemon/backend/domain/characters/entity/QCharacterMotion.java
package com.lemon.backend.domain.characters.entity;
========
package com.lemon.backend.domain.characters.characterMotion.entity;
>>>>>>>> 54d74913a972581e97443a911a4cad0096fe7cbc:Lemon/backend/src/main/generated/com/lemon/backend/domain/characters/characterMotion/entity/QCharacterMotion.java

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCharacterMotion is a Querydsl query type for CharacterMotion
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCharacterMotion extends EntityPathBase<CharacterMotion> {

<<<<<<<< HEAD:Lemon/backend/src/main/generated/com/lemon/backend/domain/characters/entity/QCharacterMotion.java
    private static final long serialVersionUID = 1581604912L;
========
    private static final long serialVersionUID = -306039105L;
>>>>>>>> 54d74913a972581e97443a911a4cad0096fe7cbc:Lemon/backend/src/main/generated/com/lemon/backend/domain/characters/characterMotion/entity/QCharacterMotion.java

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCharacterMotion characterMotion = new QCharacterMotion("characterMotion");

    public final com.lemon.backend.domain.base.QBaseEntity _super = new com.lemon.backend.domain.base.QBaseEntity(this);

<<<<<<<< HEAD:Lemon/backend/src/main/generated/com/lemon/backend/domain/characters/entity/QCharacterMotion.java
    public final QCharacters characters;
========
    public final com.lemon.backend.domain.characters.character.entity.QCharacters characters;
>>>>>>>> 54d74913a972581e97443a911a4cad0096fe7cbc:Lemon/backend/src/main/generated/com/lemon/backend/domain/characters/characterMotion/entity/QCharacterMotion.java

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final BooleanPath isDeleted = _super.isDeleted;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

<<<<<<<< HEAD:Lemon/backend/src/main/generated/com/lemon/backend/domain/characters/entity/QCharacterMotion.java
    public final QMotion motion;
========
    public final com.lemon.backend.domain.characters.motion.entity.QMotion motion;
>>>>>>>> 54d74913a972581e97443a911a4cad0096fe7cbc:Lemon/backend/src/main/generated/com/lemon/backend/domain/characters/characterMotion/entity/QCharacterMotion.java

    public final StringPath url = createString("url");

    public QCharacterMotion(String variable) {
        this(CharacterMotion.class, forVariable(variable), INITS);
    }

    public QCharacterMotion(Path<? extends CharacterMotion> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCharacterMotion(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCharacterMotion(PathMetadata metadata, PathInits inits) {
        this(CharacterMotion.class, metadata, inits);
    }

    public QCharacterMotion(Class<? extends CharacterMotion> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
<<<<<<<< HEAD:Lemon/backend/src/main/generated/com/lemon/backend/domain/characters/entity/QCharacterMotion.java
        this.characters = inits.isInitialized("characters") ? new QCharacters(forProperty("characters"), inits.get("characters")) : null;
        this.motion = inits.isInitialized("motion") ? new QMotion(forProperty("motion")) : null;
========
        this.characters = inits.isInitialized("characters") ? new com.lemon.backend.domain.characters.character.entity.QCharacters(forProperty("characters"), inits.get("characters")) : null;
        this.motion = inits.isInitialized("motion") ? new com.lemon.backend.domain.characters.motion.entity.QMotion(forProperty("motion")) : null;
>>>>>>>> 54d74913a972581e97443a911a4cad0096fe7cbc:Lemon/backend/src/main/generated/com/lemon/backend/domain/characters/characterMotion/entity/QCharacterMotion.java
    }

}

