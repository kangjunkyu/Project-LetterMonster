package com.lemon.backend.domain.sketchbook.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSketchbook is a Querydsl query type for Sketchbook
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSketchbook extends EntityPathBase<Sketchbook> {

    private static final long serialVersionUID = -852380817L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSketchbook sketchbook = new QSketchbook("sketchbook");

    public final com.lemon.backend.domain.base.QBaseEntity _super = new com.lemon.backend.domain.base.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final BooleanPath isDeleted = _super.isDeleted;

    public final BooleanPath isPublic = createBoolean("isPublic");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final StringPath name = createString("name");

    public final StringPath ShareLink = createString("ShareLink");

    public final com.lemon.backend.domain.users.entity.QUsers users;

    public QSketchbook(String variable) {
        this(Sketchbook.class, forVariable(variable), INITS);
    }

    public QSketchbook(Path<? extends Sketchbook> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSketchbook(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSketchbook(PathMetadata metadata, PathInits inits) {
        this(Sketchbook.class, metadata, inits);
    }

    public QSketchbook(Class<? extends Sketchbook> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.users = inits.isInitialized("users") ? new com.lemon.backend.domain.users.entity.QUsers(forProperty("users")) : null;
    }

}

