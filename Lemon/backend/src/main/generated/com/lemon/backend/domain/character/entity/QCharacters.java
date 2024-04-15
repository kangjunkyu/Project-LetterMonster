package com.lemon.backend.domain.character.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.lemon.backend.domain.characters.character.entity.Characters;
import com.lemon.backend.domain.characters.characterMotion.entity.CharacterMotion;
import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCharacters is a Querydsl query type for Characters
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCharacters extends EntityPathBase<Characters> {

    private static final long serialVersionUID = 1758157832L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCharacters characters = new QCharacters("characters");

    public final com.lemon.backend.domain.base.QBaseEntity _super = new com.lemon.backend.domain.base.QBaseEntity(this);

    public final ListPath<CharacterMotion, com.lemon.backend.domain.characterMotion.entity.QCharacterMotion> characterMotionList = this.<CharacterMotion, com.lemon.backend.domain.characterMotion.entity.QCharacterMotion>createList("characterMotionList", CharacterMotion.class, com.lemon.backend.domain.characterMotion.entity.QCharacterMotion.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final BooleanPath isDeleted = _super.isDeleted;

    public final ListPath<com.lemon.backend.domain.letter.entity.Letter, com.lemon.backend.domain.letter.entity.QLetter> letterList = this.<com.lemon.backend.domain.letter.entity.Letter, com.lemon.backend.domain.letter.entity.QLetter>createList("letterList", com.lemon.backend.domain.letter.entity.Letter.class, com.lemon.backend.domain.letter.entity.QLetter.class, PathInits.DIRECT2);

    public final BooleanPath mainCharacter = createBoolean("mainCharacter");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final StringPath nickname = createString("nickname");

    public final com.lemon.backend.domain.users.entity.QUsers users;

    public QCharacters(String variable) {
        this(Characters.class, forVariable(variable), INITS);
    }

    public QCharacters(Path<? extends Characters> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCharacters(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCharacters(PathMetadata metadata, PathInits inits) {
        this(Characters.class, metadata, inits);
    }

    public QCharacters(Class<? extends Characters> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.users = inits.isInitialized("users") ? new com.lemon.backend.domain.users.entity.QUsers(forProperty("users")) : null;
    }

}

