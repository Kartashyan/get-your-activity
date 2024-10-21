import { ref, watch } from "vue";

export function useWatchSearchParam() {
  const searchQuery = ref(new URLSearchParams(window.location.hash.slice(1)).get('query') || "");

  window.addEventListener('hashchange', () => {
    const newQuery = new URLSearchParams(window.location.hash.slice(1)).get('query') || "";
    if (searchQuery.value !== newQuery) {
      searchQuery.value = newQuery;
    }
  });

  watch(
    searchQuery,
    (newQuery) => {
      const url = new URL(window.location.href);
      const params = new URLSearchParams(url.hash.slice(1));
      if (newQuery) {
        params.set('query', newQuery);
      } else {
        params.delete('query');
      }
      url.hash = params.toString();
      history.replaceState(null, '', url.toString());
    }
  );

  return { searchQuery };
}