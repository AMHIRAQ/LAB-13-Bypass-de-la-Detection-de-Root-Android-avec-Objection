# 🛡️ LAB 13 — Validation du bypass de la détection de root Android avec Objection

> **Objectif :** Découvrir comment utiliser Objection afin de neutraliser les mécanismes de détection de root d'une application Android de démonstration, observer les effets de l'instrumentation dynamique et documenter les résultats obtenus dans un environnement strictement autorisé.

---

# 📖 Présentation

De nombreuses applications Android implémentent des mécanismes de détection de root afin de limiter certaines fonctionnalités ou renforcer leur posture de sécurité.

Ce laboratoire a pour objectif de vous familiariser avec **Objection**, une interface en ligne de commande reposant sur **Frida**, permettant d'interagir dynamiquement avec une application Android en cours d'exécution.

Vous apprendrez notamment à :

* préparer un environnement compatible avec Objection ;
* connecter un appareil Android instrumenté ;
* appliquer des mécanismes de neutralisation des vérifications de root ;
* observer le comportement de l'application avant et après instrumentation ;
* documenter méthodiquement les résultats obtenus.

---

# 🎯 Objectifs pédagogiques

À l'issue de ce laboratoire, vous serez capable de :

* Installer et configurer Objection ;
* Préparer un environnement Frida fonctionnel ;
* Utiliser Objection pour instrumenter une application Android ;
* Neutraliser des mécanismes courants de détection de root ;
* Valider les résultats observés ;
* Produire une documentation technique claire et professionnelle.

---

# ⚠️ Cadre légal et éthique

Ce laboratoire est réalisé exclusivement dans un **environnement pédagogique autorisé**.

## ✅ Autorisé

* Utiliser un appareil ou un émulateur dédié au laboratoire ;
* Tester une application prévue à des fins pédagogiques ;
* Observer les mécanismes de détection de root ;
* Documenter les résultats obtenus.

## ❌ Interdit

* Tester des applications tierces sans autorisation ;
* Réaliser des activités offensives hors périmètre ;
* Manipuler des données appartenant à des tiers ;
* Perturber des environnements de production.

---

# ⚙️ Prérequis

## Environnement

* Windows, macOS ou Linux ;
* Droits administrateur ou sudo ;
* Python 3.8 ou supérieur ;
* pip ;
* Android Platform Tools (ADB).

## Appareil Android

* Android 8.0 ou supérieur ;
* Options développeur activées ;
* Débogage USB activé ;
* Câble USB ou émulateur Android.

## Outils requis

* Frida ;
* frida-tools ;
* frida-server ;
* Objection ;
* Une application de démonstration intégrant une détection de root.

---

# 📂 Structure recommandée du projet

```text
LAB-13-Objection/
├── captures/
├── preuves/
│   ├── installation/
│   ├── connexion/
│   ├── objection/
│   ├── validation/
│   └── bonus/
├── rapport_final.md
├── checklist_fin.md
└── README.md
```

---

# 🚀 Déroulement du laboratoire

## Étape 1 — Installation d'Objection

### Installation via pipx (recommandée)

```bash
pip install --user pipx
pipx ensurepath
pipx install objection
```

### Installation via pip

```bash
pip install --upgrade objection
```

### Vérification

```bash
objection --version
objection --help
```

### Vérifications attendues

* [ ] Objection installé ;
* [ ] Commande accessible ;
* [ ] Version affichée correctement.

---

## Étape 2 — Préparation de l'environnement Frida

### Identifier l'architecture Android

```bash
adb shell getprop ro.product.cpu.abi
```

### Déployer frida-server

```bash
adb push frida-server /data/local/tmp/
adb shell chmod 755 /data/local/tmp/frida-server
```

### Démarrer le service

```bash
adb shell "/data/local/tmp/frida-server -l 0.0.0.0"
```

### Redirection des ports

```bash
adb forward tcp:27042 tcp:27042
adb forward tcp:27043 tcp:27043
```

### Vérification

```bash
frida-ps -Uai
```

---

## Étape 3 — Instrumentation avec Objection

Deux approches sont possibles.

### Injection au lancement (Spawn)

```bash
objection -g com.example.rootcheck explore \
  --startup-command "android root disable"
```

### Attachement à une application déjà ouverte (Attach)

Lancez l'application normalement puis :

```bash
objection -g com.example.rootcheck explore
```

Dans la console Objection :

```text
android root disable
```

---

## Étape 4 — Comprendre la neutralisation

La commande :

```text
android root disable
```

met généralement en place plusieurs hooks Java permettant notamment de :

* masquer certaines propriétés système ;
* empêcher la détection de fichiers associés au root ;
* neutraliser certaines commandes exécutées par l'application ;
* désactiver des bibliothèques courantes de détection de root.

Cette approche couvre principalement les mécanismes implémentés côté Java.

---

## Étape 5 — Validation des résultats

### Avant instrumentation

Documenter :

* le comportement initial ;
* le message affiché ;
* les restrictions observées ;
* une capture d'écran.

### Après instrumentation

Documenter :

* le nouveau comportement ;
* les journaux Objection ;
* les fonctionnalités désormais accessibles ;
* une capture d'écran.

### Vérifications

* [ ] Session Objection active ;
* [ ] Commande exécutée avec succès ;
* [ ] Résultat observé ;
* [ ] Preuves collectées.

---

## Étape 6 — Commandes utiles

Recherche de classes :

```text
android hooking search classes root
```

Recherche de méthodes :

```text
android hooking search methods isRoot
```

Lancement d'une activité :

```text
android intent launch_activity <ActivityName>
```

Aide Objection :

```text
help android root
help android hooking
```

---

## Étape 7 — Gestion des vérifications natives

Certaines applications implémentent leurs vérifications côté natif (C/C++).

### Option A : Hooks Java ciblés

Identifier les méthodes responsables puis forcer leur retour.

### Option B : Frida

Compléter l'analyse avec des scripts Frida spécialisés.

### Option C : Analyse des appels natifs

Tracer les fonctions natives pertinentes afin d'identifier les mécanismes utilisés.

---

# 📁 Collecte des preuves

Structure recommandée :

```text
preuves/
├── installation/
├── connexion/
├── objection/
├── validation/
└── bonus/
```

Chaque dossier devra contenir :

* captures d'écran ;
* commandes exécutées ;
* journaux obtenus ;
* commentaires d'analyse.

---

# 📋 Exercices à rendre

## Exercice 1 — Installation et connexion (20 pts)

Fournir les résultats ou captures de :

```bash
objection --version
frida --version
adb devices
```

---

## Exercice 2 — Session Objection (20 pts)

Montrer :

* le démarrage de frida-server ;
* l'ouverture d'une session Objection ;
* l'apparition de l'invite interactive.

---

## Exercice 3 — Validation avec Objection (40 pts)

Présenter :

* l'état avant instrumentation ;
* l'exécution de :

```text
android root disable
```

* l'état après instrumentation ;
* les journaux correspondants.

---

## Exercice 4 — Bonus (20 pts)

Identifier un mécanisme de vérification supplémentaire et documenter l'approche retenue pour son observation.

---

# 📦 Livrables attendus

* `rapport_final.md`
* `checklist_fin.md`
* Dossier `preuves/`
* Captures d'écran
* Journaux d'exécution

---

# 📝 Rapport final

Le rapport devra comporter :

## Informations générales

* Application analysée ;
* Date ;
* Auditeur.

## Méthodologie

* Installation ;
* Configuration Frida ;
* Utilisation d'Objection ;
* Validation des résultats.

## Résultats

* Observations avant instrumentation ;
* Observations après instrumentation ;
* Analyse des comportements.

## Difficultés rencontrées

* Problèmes techniques ;
* Solutions appliquées.

## Conclusion

* Enseignements tirés ;
* Limites observées ;
* Perspectives d'approfondissement.

---

# ✅ Checklist de fin

## Conformité

* [ ] Toutes les étapes ont été réalisées ;
* [ ] Les preuves ont été collectées ;
* [ ] Le rapport est complet ;
* [ ] Les observations sont correctement documentées.

## Respect du périmètre

* [ ] Aucun système tiers testé ;
* [ ] Aucune donnée réelle manipulée ;
* [ ] Environnement autorisé uniquement.

## Qualité du rapport

* [ ] Structure claire ;
* [ ] Captures pertinentes ;
* [ ] Documentation complète ;
* [ ] Conclusions justifiées.

---

# 📊 Barème (sur 20)

| Critère                        | Points |
| ------------------------------ | ------ |
| Installation et configuration  | 4      |
| Connexion Frida / Objection    | 4      |
| Validation du bypass Java      | 6      |
| Documentation et preuves       | 3      |
| Bonus (analyse complémentaire) | 2      |
| Qualité du rapport             | 1      |
| **Total**                      | **20** |

---

# 📚 Références utiles

* Documentation officielle Objection
* Documentation Frida
* Android Platform Tools (ADB)
* RootBeer (bibliothèque de détection de root)

---

# 🎓 Conclusion

Ce laboratoire vous a permis de découvrir comment utiliser **Objection** pour observer et neutraliser des mécanismes courants de détection de root Android dans un environnement pédagogique contrôlé.

Vous avez appris à préparer une chaîne d'instrumentation complète basée sur Frida, à valider les résultats obtenus et à documenter rigoureusement vos observations.

Ces compétences constituent une base importante pour comprendre les mécanismes de protection des applications mobiles et les techniques d'analyse dynamique employées lors d'évaluations de sécurité réalisées dans un cadre strictement autorisé.

---

### Auteur

**Cours :** Sécurité des applications mobiles
**Laboratoire :** LAB 13 — Validation du bypass de la détection de root Android avec Objection
**Version :** 1.0
**Licence :** Usage pédagogique uniquement.
