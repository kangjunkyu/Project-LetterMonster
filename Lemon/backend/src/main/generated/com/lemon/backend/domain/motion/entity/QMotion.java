package com.lemon.backend.domain.motion.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.lemon.backend.domain.characters.characterMotion.entity.CharacterMotion;
import com.lemon.backend.domain.characters.motion.entity.Motion;
import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMotion is a Querydsl query type for Motion
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMotion extends EntityPathBase<Motion> {

    private static final long serialVersionUID = -221432591L;

    public static final QMotion motion = new QMotion("motion");

    public final com.lemon.backend.domain.base.QBaseEntity _super = new com.lemon.backend.domain.base.QBaseEntity(this);

    public final ListPath<CharacterMotion, com.lemon.backend.domain.characterMotion.entity.QCharacterMotion> characterMotionList = this.<CharacterMotion, com.lemon.backend.domain.characterMotion.entity.QCharacterMotion>createList("characterMotionList", CharacterMotion.class, com.lemon.backend.domain.characterMotion.entity.QCharacterMotion.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final BooleanPath isDeleted = _super.isDeleted;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final StringPath name = createString("name");

    public QMotion(String variable) {
        super(Motion.class, forVariable(variable));
    }

    public QMotion(Path<? extends Motion> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMotion(PathMetadata metadata) {
        super(Motion.class, metadata);
    }

}

