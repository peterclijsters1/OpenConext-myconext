<script>
    import I18n from "i18n-js";
    import {onMount} from 'svelte';
    import {conf, links} from "../stores/conf";
    import Button from "../components/Button.svelte";
    import Verification from "../components/Verification.svelte";
    import Spinner from "../components/Spinner.svelte";
    import {fetchServiceNameByHash} from "../api";
    import Cookies from "js-cookie";
    import {cookieNames} from "../constants/cookieNames";
    import phone from "../icons/redesign/undraw_mobile_interface_re_1vv9.svg";
    import ButtonContainer from "../components/ButtonContainer.svelte";
    import ImageContainer from "../components/ImageContainer.svelte";
    import {navigate} from "svelte-routing";
    import {proceed} from "../utils/sso";

    let serviceName = null;
    let explanation = null;
    let showSpinner = true;
    let isNew = false;
    let hash = null;

    onMount(() => {
        $links.displayBackArrow = false;

        const urlSearchParams = new URLSearchParams(window.location.search);

        explanation = urlSearchParams.get("explanation");
        isNew = urlSearchParams.get("new") === "true"

        hash = urlSearchParams.get("h");
        const email = urlSearchParams.get("email");
        if (email) {
            Cookies.set(cookieNames.USERNAME, decodeURIComponent(email), {
                expires: 365,
                secure: true,
                sameSite: "Lax"
            });
        }
        fetchServiceNameByHash(hash).then(res => {
            serviceName = res.name;
            showSpinner = false;
        });
    });

</script>

<style>

    h2.new-account {
        color: var(--color-primary-green);
    }

    p.explanation {
        margin: 15px 0;
    }

</style>
{#if showSpinner}
    <Spinner/>
{/if}

{#if isNew && false}
    <h2 class="header new-account">{I18n.t("nudgeApp.new")}</h2>
{/if}
{#if explanation && false}
    <div class="verification-container">
        <Verification explanation={explanation} verified={true}/>
    </div>
{/if}
<h2 class="header">{I18n.t("nudgeApp.header")}</h2>
<ImageContainer icon={phone}/>
<p class="explanation">{@html I18n.t("nudgeApp.info")}</p>
<ButtonContainer>
    <Button className="cancel" href={I18n.t("nudgeApp.noLink")} onClick={() => proceed($conf.magicLinkUrl)}
            label={I18n.t("nudgeApp.no")}/>
    <Button href={I18n.t("nudgeApp.yesLink")} onClick={() => navigate(`/getapp?h=${hash}`)}
            label={I18n.t("nudgeApp.yes")}/>
</ButtonContainer>

