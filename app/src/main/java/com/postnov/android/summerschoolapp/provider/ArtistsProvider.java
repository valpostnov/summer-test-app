package com.postnov.android.summerschoolapp.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;

import com.postnov.android.summerschoolapp.db.ArtistsDatabase;
import com.postnov.android.summerschoolapp.db.SelectionBuilder;

/**
 * Created by postnov on 25.03.2016.
 */
public class ArtistsProvider extends ContentProvider{

    ArtistsDatabase mDatabaseHelper;

    private static final String AUTHORITY = ArtistsContract.CONTENT_AUTHORITY;

    public static final int ROUTE_ARTISTS = 1;

    public static final int ROUTE_ARTISTS_ID = 2;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sUriMatcher.addURI(AUTHORITY, "artists", ROUTE_ARTISTS);
        sUriMatcher.addURI(AUTHORITY, "artists/*", ROUTE_ARTISTS_ID);
    }

    @Override
    public boolean onCreate() {
        mDatabaseHelper = new ArtistsDatabase(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteDatabase db = mDatabaseHelper.getReadableDatabase();
        SelectionBuilder builder = new SelectionBuilder();
        int uriMatch = sUriMatcher.match(uri);
        switch (uriMatch) {
            case ROUTE_ARTISTS_ID:
                // Вернуть одну запись по ID
                String id = uri.getLastPathSegment();
                builder.where(ArtistsContract.Artist._ID + "=?", id);
            case ROUTE_ARTISTS:
                // Вернуть все записи
                builder.table(ArtistsContract.Artist.TABLE_NAME)
                        .where(selection, selectionArgs);
                Cursor c = builder.query(db, projection, sortOrder);

                Context ctx = getContext();
                assert ctx != null;
                c.setNotificationUri(ctx.getContentResolver(), uri);
                return c;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case ROUTE_ARTISTS:
                return ArtistsContract.Artist.CONTENT_TYPE;
            case ROUTE_ARTISTS_ID:
                return ArtistsContract.Artist.CONTENT_ITEM_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        final SQLiteDatabase db = mDatabaseHelper.getWritableDatabase();
        assert db != null;
        final int match = sUriMatcher.match(uri);
        Uri result;
        switch (match) {
            case ROUTE_ARTISTS:
                long id = db.insertOrThrow(ArtistsContract.Artist.TABLE_NAME, null, values);
                result = Uri.parse(ArtistsContract.Artist.CONTENT_URI + "/" + id);
                break;
            case ROUTE_ARTISTS_ID:
                throw new UnsupportedOperationException("Insert not supported on URI: " + uri);
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        // Отправляем broadcast сообщение чтобы обновить UI.
        Context ctx = getContext();
        assert ctx != null;
        ctx.getContentResolver().notifyChange(uri, null, false);
        return result;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SelectionBuilder builder = new SelectionBuilder();
        final SQLiteDatabase db = mDatabaseHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int count;
        switch (match) {
            case ROUTE_ARTISTS:
                count = builder.table(ArtistsContract.Artist.TABLE_NAME)
                        .where(selection, selectionArgs)
                        .delete(db);
                break;
            case ROUTE_ARTISTS_ID:
                String id = uri.getLastPathSegment();
                count = builder.table(ArtistsContract.Artist.TABLE_NAME)
                        .where(ArtistsContract.Artist._ID + "=?", id)
                        .where(selection, selectionArgs)
                        .delete(db);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        // Отправляем broadcast сообщение чтобы обновить UI.
        Context ctx = getContext();
        assert ctx != null;
        ctx.getContentResolver().notifyChange(uri, null, false);
        return count;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        SelectionBuilder builder = new SelectionBuilder();
        final SQLiteDatabase db = mDatabaseHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int count;
        switch (match) {
            case ROUTE_ARTISTS:
                count = builder.table(ArtistsContract.Artist.TABLE_NAME)
                        .where(selection, selectionArgs)
                        .update(db, values);
                break;
            case ROUTE_ARTISTS_ID:
                String id = uri.getLastPathSegment();
                count = builder.table(ArtistsContract.Artist.TABLE_NAME)
                        .where(ArtistsContract.Artist._ID + "=?", id)
                        .where(selection, selectionArgs)
                        .update(db, values);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        Context ctx = getContext();
        assert ctx != null;
        ctx.getContentResolver().notifyChange(uri, null, false);
        return count;
    }
}
