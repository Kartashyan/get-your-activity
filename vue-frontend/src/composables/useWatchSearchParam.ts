import { ref, watch } from "vue";
import { useRoute, useRouter } from "vue-router";

export function useWatchSearchParam() {
  const route = useRoute();
  const router = useRouter();

  const searchQuery = ref((route.query.query as string) || "");

  watch(
    searchQuery,
    (newQuery) => {
      router.replace({ query: { ...route.query, query: newQuery || undefined } });
    }
  );

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