import * as admin from "firebase-admin";
import { computeEtas } from "./etaService.js";

const db = admin.firestore();

export async function updatePlaceAggregate(placeId: string) {
  const now = admin.firestore.Timestamp.now();

  const presenceSnap = await db.collection("presence")
    .where("placeId", "==", placeId)
    .where("expiresAt", ">", now)
    .get();

  const countHere = presenceSnap.size;

  // TODO: scope interests to active plans for this place/time.
  const interestsSnap = await db.collection("interests").get();
  const countInterested = interestsSnap.size;

  // Compute ETAs for a small sample (cap to 10)
  const interestedUserIds = interestsSnap.docs
    .map(d => d.data().userId as string)
    .slice(0, 10);

  const etas = await computeEtas(interestedUserIds, placeId);

  await db.doc(`place_aggregates/${placeId}`).set({
    countHere,
    countInterested,
    interestedEtas: etas,
    updatedAt: now
  }, { merge: true });
}
