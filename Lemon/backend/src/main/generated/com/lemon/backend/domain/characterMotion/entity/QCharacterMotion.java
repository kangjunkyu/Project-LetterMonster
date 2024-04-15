package com.lemon.backend.domain.characterMotion.entity;

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

    private static final long serialVersionUID = 1574253963L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCharacterMotion characterMotion = new QCharacterMotion("characterMotion");

    public final com.lemon.backend.domain.base.QBaseEntity _super = new com.lemon.backend.domain.base.QBaseEntity(this);

    public final com.lemon.backend.domain.character.entity.QCharacters characters;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final BooleanPath isDeleted = _super.isDeleted;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final com.lemon.backend.domain.motion.entity.QMotion motion;

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
        this.characters = inits.isInitialized("characters") ? new com.lemon.backend.domain.character.entity.QCharacters(forProperty("characters"), inits.get("characters")) : null;
        this.motion = inits.isInitialized("motion") ? new com.lemon.backend.domain.motion.entity.QMotion(forProperty("motion")) : null;
    }

}

