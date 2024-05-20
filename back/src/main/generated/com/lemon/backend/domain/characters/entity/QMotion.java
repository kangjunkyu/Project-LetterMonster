package com.lemon.backend.domain.characters.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QMotion is a Querydsl query type for Motion
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMotion extends EntityPathBase<Motion> {

    private static final long serialVersionUID = 1133813925L;

    public static final QMotion motion = new QMotion("motion");

    public final com.lemon.backend.domain.base.QBaseEntity _super = new com.lemon.backend.domain.base.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final StringPath gifUrl = createString("gifUrl");

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

