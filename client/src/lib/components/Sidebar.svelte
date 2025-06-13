<!-- src/lib/components/Sidebar.svelte -->
<script>
  // 'open' will be passed as a prop from the parent
  // We'll use this to conditionally apply a CSS class.
  export let open = false;
</script>

<!-- 
  The <aside> element is semantically correct for a sidebar.
  We use Svelte's `class:` directive. The 'open' class will be
  added to the element ONLY when the `open` prop is true.
-->
<aside class:open>
  <div class="sidebar-content">
    <h2>Menu</h2>
    <nav>
      <a href="/">Home</a>
      <a href="/tasks">Tasks</a>
      <a href="/contact">Contact</a>
    </nav>
    <p>Some extra info here.</p>
  </div>
</aside>

<style>
  aside {
    position: fixed; /* Stays in place even when you scroll */
    top: 0;
    left: 0;
    height: 100vh; /* Full viewport height */
    width: 250px; /* Or whatever width you want */
    background-color: #333;
    color: white;
    box-shadow: 2px 0 5px rgba(0, 0, 0, 0.5);

    /* This is the magic: hide it off-screen to the left */
    transform: translateX(-100%);

    /* Add a smooth transition for the transform property */
    transition: transform 0.3s ease-in-out;
    z-index: 100; /* Ensure it's on top of other content */
  }

  /* This class will be applied when the sidebar should be visible */
  aside.open {
    transform: translateX(0); /* Move it back to its original position */
  }

  .sidebar-content {
    padding: 1.5rem;
  }

  nav {
    display: flex;
    flex-direction: column;
    margin-top: 1rem;
  }

  nav a {
    color: white;
    text-decoration: none;
    padding: 0.75rem 0;
    font-size: 1.2rem;
    transition: background-color 0.2s;
  }

  nav a:hover {
    background-color: #444;
  }
</style>