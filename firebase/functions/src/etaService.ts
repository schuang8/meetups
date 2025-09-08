// Minimal placeholder ETA service (no external calls).
// Replace with Google Distance Matrix and caching.
export async function computeEtas(userIds: string[], placeId: string) {
  const minutes = 10;
  const result: Record<string, { mode: string, etaMins: number }> = {};
  userIds.forEach(uid => result[uid] = { mode: "driving", etaMins: minutes });
  return result;
}
