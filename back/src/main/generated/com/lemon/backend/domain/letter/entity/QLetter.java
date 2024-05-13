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

    public final StringPath content = createString("content");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final BooleanPath isDeleted = _super.isDeleted;

    public final BooleanPath isPublic = createBoolean("isPublic");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final com.lemon.backend.domain.users.user.entity.QUsers receiver;

    public final com.lemon.backend.domain.users.user.entity.QUsers sender;

    public final com.lemon.backend.domain.sketchbook.entity.QSketchbookCharacterMotion sketchbookCharacterMotion;

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
        this.receiver = inits.isInitialized("receiver") ? new com.lemon.backend.domain.users.user.entity.QUsers(forProperty("receiver")) : null;
        this.sender = inits.isInitialized("sender") ? new com.lemon.backend.domain.users.user.entity.QUsers(forProperty("sender")) : null;
        this.sketchbookCharacterMotion = inits.isInitialized("sketchbookCharacterMotion") ? new com.lemon.backend.domain.sketchbook.entity.QSketchbookCharacterMotion(forProperty("sketchbookCharacterMotion"), inits.get("sketchbookCharacterMotion")) : null;
    }

}

