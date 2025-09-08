import * as functions from "firebase-functions";
import * as admin from "firebase-admin";
import { updatePlaceAggregate } from "./aggregateUpdater.js";

admin.initializeApp();
export const onPresenceWrite = functions.firestore
  .document("presence/{uid}")
  .onWrite(async (change, context) => {
    const before = change.before.exists ? change.before.data() : null;
    const after = change.after.exists ? change.after.data() : null;
    const placeId = after?.placeId ?? before?.placeId;
    if (!placeId) return null;
    await updatePlaceAggregate(placeId);
    return null;
  });

export const onInterestWrite = functions.firestore
  .document("interests/{id}")
  .onWrite(async (change, context) => {
    const after = change.after.exists ? change.after.data() : null;
    const planId = after?.planId ?? change.before.data()?.planId;
    // TODO: resolve plan -> placeId; for now, skip if missing
    return null;
  });

export const health = functions.https.onRequest(async (_req, res) => {
  res.status(200).send({ ok: true, time: new Date().toISOString() });
});
