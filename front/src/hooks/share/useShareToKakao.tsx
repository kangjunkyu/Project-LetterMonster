import { useState, useEffect } from "react";

type ScriptStatus = "idle" | "loading" | "ready" | "error";

function useShareToKakao(src: string): ScriptStatus {
  // Keep track of script status
  const [status, setStatus] = useState<ScriptStatus>(src ? "loading" : "idle");

  useEffect(() => {
    // Allow falsy src value if waiting on other data needed for constructing the script URL passed to this hook.
    if (!src) {
      setStatus("idle");
      return;
    }

    // Fetch existing script element by src
    let script: HTMLScriptElement | null = document.querySelector(
      `script[src="${src}"]`
    );

    if (!script) {
      // Create script
      script = document.createElement("script");
      script.src = src;
      script.async = true;
      script.setAttribute("data-status", "loading");
      // Add script to document body
      document.body.appendChild(script);

      // Store status in attribute on script
      const setAttributeFromEvent = (event: Event) => {
        script?.setAttribute(
          "data-status",
          event.type === "load" ? "ready" : "error"
        );
      };

      script.addEventListener("load", setAttributeFromEvent);
      script.addEventListener("error", setAttributeFromEvent);
    } else {
      // Grab existing script status from attribute and set to state.
      setStatus(script.getAttribute("data-status") as ScriptStatus);
    }

    // Script event handler to update status in state
    const setStateFromEvent = (event: Event) => {
      setStatus(event.type === "load" ? "ready" : "error");
    };

    // Add event listeners
    script.addEventListener("load", setStateFromEvent);
    script.addEventListener("error", setStateFromEvent);

    // Remove event listeners on cleanup
    return () => {
      if (script) {
        script.removeEventListener("load", setStateFromEvent);
        script.removeEventListener("error", setStateFromEvent);
      }
    };
  }, [src]); // Only re-run effect if script src changes

  return status;
}

export { useShareToKakao as useScript };
