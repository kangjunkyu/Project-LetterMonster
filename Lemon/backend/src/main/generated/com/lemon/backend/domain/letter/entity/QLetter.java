package com.lemon.backend.domain.letter.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QLetter is a Querydsl query type for Letter
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QLetter extends EntityPathBase<Letter> {

    private static final long serialVersionUID = 256473553L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QLetter letter = new QLetter("letter");

    public final com.lemon.backend.domain.base.QBaseEntity _super = new com.lemon.backend.domain.base.QBaseEntity(this);

    public final com.lemon.backend.domain.characters.entity.QCharacters characters;

    public final StringPath content = createString("content");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final BooleanPath isDeleted = _super.isDeleted;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final NumberPath<Integer> receiver = createNumber("receiver", Integer.class);

    public final NumberPath<Integer> sender = createNumber("sender", Integer.class);

    public final com.lemon.backend.domain.sketchbook.entity.QSketchbook sketchbook;

    public QLetter(String variable) {
        this(Letter.class, forVariable(variable), INITS);
    }

    public QLetter(Path<? extends Letter> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QLetter(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QLetter(PathMetadata metadata, PathInits inits) {
        this(Letter.class, metadata, inits);
    }

    public QLetter(Class<? extends Letter> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.characters = inits.isInitialized("characters") ? new com.lemon.backend.domain.characters.entity.QCharacters(forProperty("characters"), inits.get("characters")) : null;
        this.sketchbook = inits.isInitialized("sketchbook") ? new com.lemon.backend.domain.sketchbook.entity.QSketchbook(forProperty("sketchbook"), inits.get("sketchbook")) : null;
    }

}

