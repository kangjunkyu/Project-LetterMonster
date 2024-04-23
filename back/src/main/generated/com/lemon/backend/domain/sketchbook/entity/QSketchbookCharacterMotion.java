package com.lemon.backend.domain.sketchbook.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSketchbookCharacterMotion is a Querydsl query type for SketchbookCharacterMotion
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSketchbookCharacterMotion extends EntityPathBase<SketchbookCharacterMotion> {

    private static final long serialVersionUID = 276724944L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSketchbookCharacterMotion sketchbookCharacterMotion = new QSketchbookCharacterMotion("sketchbookCharacterMotion");

    public final com.lemon.backend.domain.base.QBaseEntity _super = new com.lemon.backend.domain.base.QBaseEntity(this);

    public final com.lemon.backend.domain.characters.entity.QCharacterMotion characterMotion;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final BooleanPath isDeleted = _super.isDeleted;

    public final ListPath<com.lemon.backend.domain.letter.entity.Letter, com.lemon.backend.domain.letter.entity.QLetter> letters = this.<com.lemon.backend.domain.letter.entity.Letter, com.lemon.backend.domain.letter.entity.QLetter>createList("letters", com.lemon.backend.domain.letter.entity.Letter.class, com.lemon.backend.domain.letter.entity.QLetter.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final QSketchbook sketchbook;

    public QSketchbookCharacterMotion(String variable) {
        this(SketchbookCharacterMotion.class, forVariable(variable), INITS);
    }

    public QSketchbookCharacterMotion(Path<? extends SketchbookCharacterMotion> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSketchbookCharacterMotion(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSketchbookCharacterMotion(PathMetadata metadata, PathInits inits) {
        this(SketchbookCharacterMotion.class, metadata, inits);
    }

    public QSketchbookCharacterMotion(Class<? extends SketchbookCharacterMotion> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.characterMotion = inits.isInitialized("characterMotion") ? new com.lemon.backend.domain.characters.entity.QCharacterMotion(forProperty("characterMotion"), inits.get("characterMotion")) : null;
        this.sketchbook = inits.isInitialized("sketchbook") ? new QSketchbook(forProperty("sketchbook"), inits.get("sketchbook")) : null;
    }

}

