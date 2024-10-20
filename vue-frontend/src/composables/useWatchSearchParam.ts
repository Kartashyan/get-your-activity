// useWatchSearchParam.ts
import { ref, watch } from "vue";
import { useRoute, useRouter } from "vue-router";

export function useWatchSearchParam() {
  const route = useRoute();
  const router = useRouter();

  const searchQuery = ref((route.query.query as string) || "");

  // Update the route's query parameter when searchQuery changes
  watch(
    searchQuery,
    (newQuery) => {
      router.replace({ query: { ...route.query, query: newQuery || undefined } });
    }
  );

  // Update searchQuery when the route's query parameter changes
  watch(
    () => route.query.query,
    (newQuery) => {
      const newQueryStr = (newQuery as string) || "";
      if (searchQuery.value !== newQueryStr) {
        searchQuery.value = newQueryStr;
      }
    }
  );

  return { searchQuery };
}